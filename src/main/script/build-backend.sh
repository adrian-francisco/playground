#!/bin/bash
set -euo pipefail

export AWS_PROFILE=${AWS_PROFILE-"morpheus-dev"}
MORPHEUS_APPLICATION_DOCKER_BAKERY=/Users/afrancisco/git/morpheus-application-docker-bakery
FORTIVA_DIR=/Users/afrancisco/git/fortiva
TAG_SUFFIX="_af"
AWS_PROJECT="app/test"

WORKDIR_NAME=workdir

PP_DOCKER_DIR=$(cd "$MORPHEUS_APPLICATION_DOCKER_BAKERY"; pwd)/pp-archiving
PP_DOCKER_ADMIN_DIR=$(cd "$MORPHEUS_APPLICATION_DOCKER_BAKERY"; pwd)/pp-archiving-admin
ARCHIVING_TARGET_DIR=$(cd "$FORTIVA_DIR"; pwd)/src/java/target

ARCHIVING_ZIP=$(cd "$ARCHIVING_TARGET_DIR"; echo archiving-*-backend-linux64.zip | grep -v '*' | sed 1q)
if [[ -z "$ARCHIVING_ZIP" ]]; then echo -e "\nERROR: could not find archiving-*-backend-linux64.zip file!" >&2; exit 1; fi

rm -rf "./${WORKDIR_NAME}"
mkdir -p "$WORKDIR_NAME"
WORK_DIR=$(cd "$WORKDIR_NAME"; pwd)

########### Prepare parameters ################

AWS_REGION=$(aws configure get region)
CRED=$(aws configure export-credentials)
AWS_ACCESS_KEY_ID=$(echo $CRED | jq -r '.AccessKeyId')
AWS_SECRET_ACCESS_KEY=$(echo $CRED | jq -r '.SecretAccessKey')
AWS_SESSION_TOKEN=$(echo $CRED | jq -r '.SessionToken')

AWS_ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
BASE_IMAGE_LOCATION=app/base/release
BASE_IMAGE_TAG=$(aws ecr list-images --repository-name ${BASE_IMAGE_LOCATION}/pp-archiving-base --filter 'tagStatus=TAGGED' --query 'imageIds[*].imageTag' --output text | tr '\t' '\n' | grep -v '^3\.5\.9' | sort -r | sed 1q)
OPS_TOOLS_VERSION=$(aws s3 ls s3://morpheus-platform-publish.${AWS_ACCOUNT_ID}.us-east-1.pfpt/app/project-morpheus/ops-tools/ | sed 's#^.*\(3\.5\.[^/]*\)/.*$#\1#' | sort -r | sed 1q)
S3_BUCKET_URL="s3://morpheus-platform-publish.${AWS_ACCOUNT_ID}.us-east-1.pfpt/app/project-morpheus/ops-tools/${OPS_TOOLS_VERSION}/morpheus_ops_tools-${OPS_TOOLS_VERSION}-py3-none-any.whl"
OPS_TOOLS_WHL_FILE="morpheus_ops_tools-${OPS_TOOLS_VERSION}-py3-none-any.whl"

aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.us-east-1.amazonaws.com/

########### Build pp-archiving ################

cd "$WORK_DIR"

unzip -q "$ARCHIVING_TARGET_DIR/$ARCHIVING_ZIP"
FORTIVA_DIR=$(echo fortiva-dist-* | grep -v '*' | sed 1q)

if [[ ! -d "$FORTIVA_DIR" ]]; then echo -e "\nERROR: could not find fortiva-dist-* folder!" >&2; exit 1; fi

FORTIVA_VERSION=${FORTIVA_DIR#fortiva-dist-}
echo "FORTIVA_DIR=$FORTIVA_DIR, FORTIVA_VERSION=$FORTIVA_VERSION"

cp -r $FORTIVA_DIR/* .
rm -rf "./${FORTIVA_DIR}"

cp -r "$PP_DOCKER_DIR"/* .

# Add AWS_SESSION_TOKEN as we use temporary credentials when login via SSO
sed -e $'/ARG AWS_SECRET_ACCESS_KEY/a\\\nARG AWS_SESSION_TOKEN\n' \
    -e $'/export AWS_SECRET_ACCESS_KEY/a\\\n    export AWS_SESSION_TOKEN=${AWS_SESSION_TOKEN} && \\\\' \
    Dockerfile >Dockerfile.tmp
mv Dockerfile.tmp Dockerfile

docker build -t pp-archiving:${FORTIVA_VERSION}${TAG_SUFFIX} \
    --build-arg "AWS_ACCOUNT_ID=$AWS_ACCOUNT_ID" \
    --build-arg "BASE_IMAGE_LOCATION=$BASE_IMAGE_LOCATION" \
    --build-arg "BASE_IMAGE_TAG=$BASE_IMAGE_TAG" \
    --build-arg "AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID" \
    --build-arg "AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY" \
    --build-arg "AWS_SESSION_TOKEN=$AWS_SESSION_TOKEN" \
    --build-arg "OPS_TOOLS_WHL_FILE=$OPS_TOOLS_WHL_FILE" \
    --build-arg "S3_BUCKET_URL=$S3_BUCKET_URL" \
    .
docker image tag \
    pp-archiving:${FORTIVA_VERSION}${TAG_SUFFIX} \
    ${AWS_ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com/${AWS_PROJECT}/pp-archiving:${FORTIVA_VERSION}${TAG_SUFFIX}

echo -e "\nImage pp-archiving:${FORTIVA_VERSION}${TAG_SUFFIX} successfully created.\n"
########### End of build pp-archiving ################

cd "${WORK_DIR}/../"

rm -rf "./${WORKDIR_NAME}"
mkdir -p "$WORKDIR_NAME"

########### Build pp-archiving-admin ################

cd "$WORK_DIR"

cp -r "$PP_DOCKER_ADMIN_DIR"/* .

docker build -t pp-archiving-admin:${FORTIVA_VERSION}${TAG_SUFFIX} \
    --build-arg "AWS_ACCOUNT_ID=$AWS_ACCOUNT_ID" \
    --build-arg "BUILD_TAG=${FORTIVA_VERSION}${TAG_SUFFIX}" \
    --build-arg "AWS_PROJECT=$AWS_PROJECT" \
    .

docker image tag \
    pp-archiving-admin:${FORTIVA_VERSION}${TAG_SUFFIX} \
    ${AWS_ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com/${AWS_PROJECT}/pp-archiving-admin:${FORTIVA_VERSION}${TAG_SUFFIX}

echo -e "\nImage pp-archiving-admin:${FORTIVA_VERSION}${TAG_SUFFIX} successfully created.\n"
########### End of build pp-archiving-admin ################

echo -e "\nSuccess.\nNext, push image to repo:\n\tdocker image push ${AWS_ACCOUNT_ID}.dkr.ecr.us-east-1.amazonaws.com/${AWS_PROJECT}/pp-archiving-admin:${FORTIVA_VERSION}${TAG_SUFFIX}\n\n"