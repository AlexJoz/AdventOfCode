from collections import Counter

if __name__ == '__main__':

    with open('input6.txt') as f:

        a = f.read().strip()

    print("Part1: {}".format(''.join(Counter(x).most_common()[0][0] for x in zip(*a.splitlines()))))
    print("Part2: {}".format(''.join(Counter(x).most_common()[-1][0] for x in zip(*a.splitlines()))))
