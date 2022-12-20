import doctest


class Plant:
    """A class used by a plant nursery to track what plants they have in inventory
    and how many they have of each plant.

    >>> milkweed = Plant("Narrow-leaf milkweed", "Asclepias fascicularis")
    >>> milkweed.common_name
    'Narrow-leaf milkweed'
    >>> milkweed.latin_name
    'Asclepias fascicularis'
    >>> milkweed.inventory
    0
    >>> milkweed.update_inventory(2)
    >>> milkweed.inventory
    2
    >>> milkweed.update_inventory(3)
    >>> milkweed.inventory
    5
    """

    def __init__(self, common_name, latin_name):
        self.common_name = common_name
        self.latin_name = latin_name
        self.inventory = 0

    def update_inventory(self, amount):
        self.inventory += amount


class MoviePurchase:
    """A class that represents movie purchases on YouTube,
    tracking the title and cost of each movie bought,
    as well as the number of times the movie is watched.

    >>> ponyo = MoviePurchase("Ponyo", 19.99)
    >>> ponyo.title
    'Ponyo'
    >>> ponyo.cost
    19.99
    >>> ponyo.times_watched
    0
    >>> ponyo.watch()
    >>> ponyo.watch()
    >>> ponyo.times_watched
    2
    """

    def __init__(self, title, cost):
        self.title = title
        self.cost = cost
        self.times_watched = 0

    def watch(self):
        self.times_watched += 1


class Player:
    """A class that represents a player in a video game,
    tracking their name and health.

    >>> player = Player("Mario")
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


class Clothing:
    """A class that represents pieces of clothing in a closet,
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


doctest.testmod(verbose=True)
