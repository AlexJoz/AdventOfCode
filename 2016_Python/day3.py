if __name__ == '__main__':

    with open('input3.txt') as f:
        valid_triangles_num = 0
        for line in f:
            a = sorted([int(a) for a in line.split()])
            if int(a[0])+int(a[1]) > int(a[2]):
                valid_triangles_num += 1
        print ("part 1: {}".format(valid_triangles_num))

    with open('input3.txt') as f:
        z =[]
        x=[]
        c =[]
        valid_triangles_num_p2 = 0

        for line in f:
            a = line.split()
            z.append(int(a[0]))
            x.append(int(a[1]))
            c.append(int(a[2]))

        z=z+x+c

        hui = [sorted(z[i:i + 3]) for i in range(0, len(z), 3)]

        for line in hui:
            if int(line[0])+int(line[1]) > int(line[2]):
                        valid_triangles_num_p2 += 1

        print("part 2: {}".format(valid_triangles_num_p2))


'''
--- Day 3: Squares With Three Sides ---

Now that you can think clearly, you move deeper into the labyrinth of hallways and office furniture that makes up this part of Easter Bunny HQ. This must be a graphic design department; the walls are covered in specifications for triangles.

Or are they?

The design document gives the side lengths of each triangle it describes, but... 5 10 25? Some of these aren't triangles. You can't help but mark the impossible ones.

In a valid triangle, the sum of any two sides must be larger than the remaining side. For example, the "triangle" given above is impossible, because 5 + 10 is not larger than 25.

In your puzzle input, how many of the listed triangles are possible?

Your puzzle answer was 1050.
--- Part Two ---

Now that you've helpfully marked up their design documents, it occurs to you that triangles are specified in groups of three vertically. Each set of three numbers in a column specifies a triangle. Rows are unrelated.

For example, given the following specification, numbers with the same hundreds digit would be part of the same triangle:

101 301 501
102 302 502
103 303 503
201 401 601
202 402 602
203 403 603

In your puzzle input, and instead reading by columns, how many of the listed triangles are possible?

Your puzzle answer was 1921.
'''