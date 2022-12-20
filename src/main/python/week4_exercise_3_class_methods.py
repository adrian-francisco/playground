import doctest
import random


class Thneed:
    def __init__(self, color, size):
        self.color = color
        self.size = size

    @classmethod
    def make_thneed(cls, size):
        """
        Returns a new instance of a Thneed with
        a random color and the given size.
        >>> rand_thneed = Thneed.make_thneed("small")
        >>> isinstance(rand_thneed, Thneed)
        True
        >>> rand_thneed.size
        'small'
        >>> isinstance(rand_thneed.color, str)
        True
        """
        colors = ["red", "green", "blue"]
        return cls(random.choice(colors), size)


class Cat:
    def __init__(self, name, owner, lives=9):
        self.name = name
        self.owner = owner
        self.lives = lives

    @classmethod
    def adopt_random_cat(cls, owner):
        """
        Returns a new instance of a Cat with the given owner,
        a randomly chosen name and a random number of lives.
        >>> randcat = Cat.adopt_random_cat("Ifeoma")
        >>> isinstance(randcat, Cat)
        True
        >>> randcat.owner
        'Ifeoma'
        """
        names = ["cat1", "cat2", "cat3"]
        lives = random.randrange(1, 9)
        return cls(random.choice(names), owner, lives)


doctest.testmod(verbose=True)
