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