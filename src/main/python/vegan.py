import pandas as pd

df = pd.read_csv('vegan_recipes.csv')

f = open("vegan_recipes_preparations.txt", "w")
for preparation in df['preparation']:
    for line in preparation.split('\n'):
        if len(line.strip()) == 0:
            continue
        f.write(line + "\n")
f.close()

f = open("vegan_recipes_titles.txt", "w")
for title in df['title']:
    f.write(title + "\n")
f.close()
