import doctest


class StudentGrade:
    """Represents the grade for a student in a class
    and indicates when a grade is failing.
    For all students, 159 points is considered a failing grade.

    >>> grade1 = StudentGrade("Arfur Artery", 300)
    >>> grade1.is_failing()
    False
    >>> grade2 = StudentGrade("MoMo OhNo", 158)
    >>> grade2.is_failing()
    True
    >>> grade1.failing_grade
    159
    >>> grade2.failing_grade
    159
    >>> StudentGrade.failing_grade
    159
    >>>
    """

    failing_grade = 159

    def __init__(self, student_name, num_points):
        self.student_name = student_name
        self.num_points = num_points

    def is_failing(self):
        return self.num_points < StudentGrade.failing_grade


class Article:
    """Represents an article on an educational website.
    The license for all articles should be "CC-BY-NC-SA".

    >>> article1 = Article("Logic", "Samuel Tarín")
    >>> article1.get_byline()
    'By Samuel Tarín, License: CC-BY-NC-SA'
    >>> article2 = Article("Loops", "Pamela Fox")
    >>> article2.get_byline()
    'By Pamela Fox, License: CC-BY-NC-SA'
    """

    license = "CC-BY-NC-SA"

    def __init__(self, title, author):
        self.title = title
        self.author = author

    def get_byline(self):
        return f"By {self.author}, License: {self.license}"


doctest.testmod(verbose=True)
