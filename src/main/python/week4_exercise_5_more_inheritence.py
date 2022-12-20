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


class Cat(Animal):
    """
    >>> adult = Cat("Winston", 12)
    >>> adult.name
    'Winston'
    >>> adult.age
    12
    >>> adult.play_multiplier
    3
    >>> kitty = Cat("Kurty", 0.5)
    >>> kitty.name
    'Kurty'
    >>> kitty.age
    0.5
    >>> kitty.play_multiplier
    6
    """

    species_name = "Domestic cat"
    scientific_name = "Felis silvestris catus"
    calories_needed = 200
    play_multiplier = 3

    def __init__(self, name, age):
        super().__init__(name, age)
        if age < 1:
            self.play_multiplier = 6


class Dog(Animal):
    """
    >>> spot = Dog("Spot", 5, 20)
    >>> spot.name
    'Spot'
    >>> spot.age
    5
    >>> spot.weight
    20
    >>> spot.calories_needed
    400
    >>> puppy = Dog("Poppy", 1, 7)
    >>> puppy.name
    'Poppy'
    >>> puppy.age
    1
    >>> puppy.weight
    7
    >>> puppy.calories_needed
    140
    """

    species_name = "Domestic dog"
    scientific_name = "Canis lupus familiaris"
    calories_needed = 200

    def __init__(self, name, age, weight):
        super().__init__(name, age)
        self.weight = weight
        self.calories_needed = 20 * weight


class Pet:
    def __init__(self, name, owner):
        self.is_alive = True
        self.name = name
        self.owner = owner

    def eat(self, thing):
        print(self.name + " ate a " + str(thing) + "!")

    def talk(self):
        print(self.name)


class Cat(Pet):
    """
    >>> lizzie = Cat("Lizzie", "Hunter")
    >>> lizzie.name
    'Lizzie'
    >>> lizzie.owner
    'Hunter'
    >>> lizzie.lives
    9
    >>> lizzie.is_alive
    True
    >>> lizzie.talk()
    Lizzie says meow!
    >>> lizzie.lose_life()
    >>> lizzie.lose_life()
    >>> lizzie.lose_life()
    >>> lizzie.lose_life()
    >>> lizzie.lose_life()
    >>> lizzie.lives
    4
    >>> lizzie.lose_life()
    >>> lizzie.lose_life()
    >>> lizzie.lose_life()
    >>> lizzie.lose_life()
    >>> lizzie.lives
    0
    >>> lizzie.is_alive
    False
    """

    def __init__(self, name, owner, lives=9):
        super().__init__(name, owner)
        self.lives = lives

    def talk(self):
        print(f"{self.name} says meow!")

    def lose_life(self):
        self.lives -= 1
        if self.lives <= 0:
            self.is_alive = False


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

    def eat(self, meal):
        self.calories_eaten += meal.calories
        print(f"Om nom nom yummy {meal.name}")
        if self.calories_eaten > self.calories_needed:
            self.happiness -= 1
            print("Ugh so full")

    def interact_with(self, animal2):
        self.happiness += self.interact_increment
        print(f"Yay happy fun time with {animal2.name}")


class Herbivore(Animal):
    def eat(self, meal):
        if meal.kind == "meat":
            self.happiness -= 5
        else:
            super().eat(meal)


class Carnivore(Animal):
    def eat(self, meal):
        if meal.kind == "meat":
            super().eat(meal)


class Meal:
    def __init__(self, name, kind, calories):
        self.name = name
        self.kind = kind
        self.calories = calories


class Zebra(Herbivore):
    """
    >>> zebby = Zebra("Zebby", 12)
    >>> zebby.play(2)
    WHEEE PLAY TIME!
    >>> zebby.happiness
    4
    >>> zebby.eat(Meal("Broccoli", "vegetable", 100))
    Om nom nom yummy Broccoli
    >>> zebby.calories_eaten
    100
    >>> zebby.eat(Meal("Beef", "meat", 300))
    >>> zebby.happiness
    -1
    >>> zebby.calories_eaten
    100
    """

    species_name = "Common Zebra"
    scientific_name = "Equus quagga"
    calories_needed = 15000


class Hyena(Carnivore):
    """
    >>> helen = Hyena("Helen", 12)
    >>> helen.play(2)
    WHEEE PLAY TIME!
    >>> helen.happiness
    4
    >>> helen.eat(Meal("Carrion", "meat", 300))
    Om nom nom yummy Carrion
    >>> helen.calories_eaten
    300
    >>> helen.happiness
    4
    >>> helen.eat(Meal("Broccoli", "vegetable", 100))
    >>> helen.calories_eaten
    300
    >>> helen.happiness
    4
    """

    species_name = "Striped Hyena"
    scientific_name = "Hyaena hyaena"
    calories_needed = 1000


class Pet:
    def __init__(self, name, owner):
        self.is_alive = True
        self.name = name
        self.owner = owner

    def eat(self, thing):
        print(self.name + " ate a " + str(thing) + "!")

    def talk(self):
        print(self.name)


class Dog(Pet):
    """
    >>> cooper = Dog("Cooper", "Tinu")
    >>> cooper.name
    'Cooper'
    >>> cooper.owner
    'Tinu'
    >>> cooper.talk()
    Cooper says BARK!
    """

    def talk(self):
        print(f"{self.name} says BARK!")


class NoisyDog(Dog):
    """
    >>> roxy = NoisyDog("Roxy", "Joe")
    >>> roxy.name
    'Roxy'
    >>> roxy.owner
    'Joe'
    >>> roxy.talk()
    Roxy says BARK!
    Roxy says BARK!
    Roxy says BARK!
    """

    def talk(self):
        super().talk()
        super().talk()
        super().talk()


doctest.testmod(verbose=True)
