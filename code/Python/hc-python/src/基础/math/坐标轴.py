import math

xc = 632
yc = 1312
# innerR = 56
r = 220
degrees = 49
radians = math.radians(degrees)
x = xc + math.cos(radians) * r
y = yc - math.sin(radians) * r
x1 = xc - math.cos(radians) * r
y1 = yc + math.sin(radians) * r

time = 7200
print('rsh.slide([[{0},{1}], [{2}, {3}]], {4})'.format(xc, yc, x, y, time))
print('rsh.slide([[{0},{1}], [{2}, {3}]], {4})'.format(xc, yc, x1, y1, time)) 