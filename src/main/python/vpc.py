import boto3
from graphviz import Digraph

# Initialize AWS clients
ec2_global = boto3.client("ec2")

# Specify target regions
regions = ["us-east-1", "eu-central-1", "me-central-1"]


def get_vpc_label(vpc, region):
    vpc_name = next(
        (tag["Value"] for tag in vpc.get("Tags", []) if tag["Key"] == "Name"), None
    )
    if not vpc_name:
        return None  # Skip unnamed VPCs
    return f"VPC: {vpc['VpcId']}\nCIDR: {vpc['CidrBlock']}\nName: {vpc_name}\nRegion: {region}"


def get_subnet_label(subnet):
    subnet_name = next(
        (tag["Value"] for tag in subnet.get("Tags", []) if tag["Key"] == "Name"), None
    )
    if not subnet_name:
        return None  # Skip unnamed subnets
    return f"Subnet: {subnet['SubnetId']}\nCIDR: {subnet['CidrBlock']}\nName: {subnet_name}"


# Create a single diagram for all VPCs using dot with left-to-right layout
dot = Digraph("AWS VPCs", engine="dot", filename="all_vpcs_diagram", format="png")
dot.attr(
    rankdir="LR", size="20", dpi="600"
)  # Left to right layout with high resolution

# Loop through each region
for region in regions:
    ec2 = boto3.client("ec2", region_name=region)
    vpcs = ec2.describe_vpcs()["Vpcs"]

    with dot.subgraph() as region_subgraph:
        region_subgraph.attr(label=f"Region: {region}", color="black", style="dashed")

        for vpc in vpcs:
            vpc_label = get_vpc_label(vpc, region)
            if not vpc_label:
                continue  # Skip unnamed VPCs

            vpc_id = vpc["VpcId"]
            region_subgraph.node(
                vpc_id, vpc_label, shape="box", style="filled", fillcolor="lightblue"
            )

            # Fetch subnets for the VPC
            subnets = ec2.describe_subnets(
                Filters=[{"Name": "vpc-id", "Values": [vpc_id]}]
            )["Subnets"]

            # Group subnets by availability zone and sort by name
            subnets_by_zone = {}
            for subnet in subnets:
                subnet_label = get_subnet_label(subnet)
                if not subnet_label:
                    continue  # Skip unnamed subnets
                az = subnet["AvailabilityZone"]
                subnets_by_zone.setdefault(az, []).append((subnet, subnet_label))

            for az in subnets_by_zone:
                with region_subgraph.subgraph() as az_subgraph:
                    az_subgraph.attr(label=f"AZ: {az}", color="blue", style="dotted")
                    subnets_by_zone[az].sort(key=lambda s: s[1])  # Sort by subnet name
                    for subnet, subnet_label in subnets_by_zone[az]:
                        subnet_id = subnet["SubnetId"]
                        az_subgraph.node(subnet_id, subnet_label, shape="ellipse")
                        region_subgraph.edge(
                            vpc_id, subnet_id
                        )  # Connect subnet to its VPC

# Render the diagram
dot.render()
print("VPC network diagram generated: all_vpcs_diagram.png")
