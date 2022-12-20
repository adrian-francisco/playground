import doctest


class Animal:
    species_name = "Animal"
    scientific_name = "Animalia"
    play_multiplier = 2
    interact_increment = 1

    def __init__(self, name, age=0):
        self.name = name
        self.age = age
        self.calories_eaten = 0
        self.happiness = 0

    def play(self, num_hours):
        self.happiness += num_hours * self.play_multiplier
        print("WHEEE PLAY TIME!")

    def eat(self, food):
        self.calories_eaten += food.calories
        print(f"Om nom nom yummy {food.name}")
        if self.calories_eaten > self.calories_needed:
            self.happiness -= 1
            print("Ugh so full")

    def interact_with(self, animal2):
        self.happiness += self.interact_increment
        print(f"Yay happy fun time with {animal2.name}")


class Sloth(Animal):
    """
    >>> Sloth.species_name
    "Hoffmann's two-toed sloth"
    >>> Sloth.scientific_name
    'Choloepus hoffmanni'
    >>> Sloth.calories_needed
    680
    >>> buttercup = Sloth('Buttercup', 27)
    >>> buttercup.name
    'Buttercup'
    >>> buttercup.age
    27
    """

    species_name = "Hoffmann's two-toed sloth"
    scientific_name = "Choloepus hoffmanni"
    calories_needed = 680


class Cat(Animal):
    """
    >>> Cat.species_name
    'Domestic cat'
    >>> Cat.scientific_name
    'Felis silvestris catus'
    >>> Cat.calories_needed
    200
    >>> jackson = Cat("Jackson", 8)
    >>> jackson.name
    'Jackson'
    >>> jackson.age
    8
    """

    species_name = "Domestic cat"
    scientific_name = "Felis silvestris catus"
    calories_needed = 200


class LearnableContent:
    """A base class for specific kinds of learnable content.
    All kinds have title and author attributes,
    but each kind may have additional attributes.
    """

    license = "Creative Commons"

    def __init__(self, title, author):
        self.title = title
        self.author = author

    def get_heading(self):
        return f"{self.title} by {self.author}"


class Video(LearnableContent):
    """
    >>> Video.license
    'YouTube Standard License'
    >>> dna_vid = Video('DNA', 'Megan')
    >>> dna_vid.title
    'DNA'
    >>> dna_vid.author
    'Megan'
    >>> dna_vid.get_heading()
    'DNA by Megan'
    """

    license = "YouTube Standard License"


class Article(LearnableContent):
    """
    >>> Article.license
    'CC-BY-NC-SA'
    >>> water_article = Article('Water phases', 'Lauren')
    >>> water_article.title
    'Water phases'
    >>> water_article.author
    'Lauren'
    >>> water_article.get_heading()
    'Water phases by Lauren'
    """

    license = "CC-BY-NC-SA"


class Character:
    """Represents a character in a video game,
    tracking their name and health.

    >>> player = Character("Mario")
    >>> player.name
    'Mario'
    >>> player.health
    100
    >>> player.damage(10)
    >>> player.health
    90
    >>> player.boost(5)
    >>> player.health
    95
    """

    def __init__(self, name):
        self.name = name
        self.health = 100

    def damage(self, amount):
        self.health -= amount

    def boost(self, amount):
        self.health += amount


class Boss(Character):
    """Tracks a Boss character in a video game.

    >>> mx_boss = Boss("Mx Boss Person")
    >>> mx_boss.damage(100)
    >>> mx_boss.health
    99
    >>> mx_boss.damage(10)
    >>> mx_boss.health
    98
    >>> mx_boss.boost(1)
    >>> mx_boss.health
    100
    """

    def damage(self, amount):
        self.health -= 1

    def boost(self, amount):
        self.health += amount * 2


class Clothing:
    """Represents clothing in a closet,
    tracking the color, category, and clean/dirty state.

    >>> blue_shirt = Clothing("shirt", "blue")
    >>> blue_shirt.category
    'shirt'
    >>> blue_shirt.color
    'blue'
    >>> blue_shirt.is_clean
    True
    >>> blue_shirt.wear()
    >>> blue_shirt.is_clean
    False
    >>> blue_shirt.clean()
    >>> blue_shirt.is_clean
    True
    """

    def __init__(self, category, color):
        self.category = category
        self.color = color
        self.is_clean = True

    def wear(self):
        self.is_clean = False

    def clean(self):
        self.is_clean = True


class KidsClothing(Clothing):
    """
    >>> onesie = KidsClothing("onesie", "polka dots")
    >>> onesie.wear()
    >>> onesie.is_clean
    False
    >>> onesie.clean()
    >>> onesie.is_clean
    False
    >>> dress = KidsClothing("dress", "rainbow")
    >>> dress.clean()
    >>> dress.is_clean
    True
    >>> dress.wear()
    >>> dress.is_clean
    False
    >>> dress.clean()
    >>> dress.is_clean
    False
    """

    def clean(self):
        if not self.is_clean:
            self.is_clean = False


doctest.testmod(verbose=True)
