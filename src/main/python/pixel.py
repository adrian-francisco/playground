import doctest
import copy


def flip_horizontal(pixel_data):
  """ Flips the pixels in the list so that the left-most become the right-most.
  >>> flip_horizontal([[[0, 0, 0], [255, 0, 0]], [[0, 255, 0], [0, 0, 255]]])
  [[[255, 0, 0], [0, 0, 0]], [[0, 0, 255], [0, 255, 0]]]
  >>> flip_horizontal([[[0, 0, 0], [255, 0, 0]], [[0, 255, 0], [0, 0, 255]], [[40, 50, 60], [10, 20, 30]]])
  [[[255, 0, 0], [0, 0, 0]], [[0, 0, 255], [0, 255, 0]], [[10, 20, 30], [40, 50, 60]]]
  """
  new_pixel_data = copy.deepcopy(pixel_data)
  for rows in new_pixel_data:
    i = 0
    while i < (len(rows) / 2):
      temp = rows[i]
      rows[i] = rows[-(i + 1)]
      rows[-(i + 1)] = temp
      i += 1
  return new_pixel_data

def flip_vertical(pixel_data):
  """ Flips the pixels in the list so that the top-most become the bottom-most.
  >>> flip_vertical([[[0, 0, 0], [255, 0, 0]], [[0, 255, 0], [0, 0, 255]]])
  [[[0, 255, 0], [0, 0, 255]], [[0, 0, 0], [255, 0, 0]]]
  >>> flip_vertical([[[0, 0, 0], [255, 0, 0]], [[0, 255, 0], [0, 0, 255]], [[40, 50, 60], [10, 20, 30]]])
  [[[40, 50, 60], [10, 20, 30]], [[0, 255, 0], [0, 0, 255]], [[0, 0, 0], [255, 0, 0]]]
  """
  new_pixel_data = copy.deepcopy(pixel_data)
  i = 0
  while i < (len(new_pixel_data) / 2):
      temp = new_pixel_data[i]
      new_pixel_data[i] = new_pixel_data[-(i + 1)]
      new_pixel_data[-(i + 1)] = temp
      i += 1
  return new_pixel_data

doctest.run_docstring_examples(flip_horizontal, globals(), verbose=True, name='flip_horizontal')
doctest.run_docstring_examples(flip_vertical, globals(), verbose=True, name='flip_vertical')