import numpy as np

class Taxi:
    def __init__(self):
        # part 1
        self.x = 0
        self.y = 0
        self.cur_dir = "N"

        # part 2
        self.bsize1 = 250
        self.bsize2 = 250
        self.board = np.zeros((self.bsize1 * 2, self.bsize2 * 2))
        self.row = self.bsize1
        self.col = self.bsize2
        self.answer2 = None

    def __repr__(self):
        return "{}-{}-{}".format(self.x, self.y, self.cur_dir)

    def calc_dist(self):
        return abs(self.x) + abs(self.y)

    def do_move(self, dirs, n):
        if self.cur_dir == "N":
            if dirs == "R":
                self.go_r(n)
                self.cur_dir = "E"
            elif dirs == "L":
                self.go_l(n)
                self.cur_dir = "W"

        elif self.cur_dir == "E":
            if dirs == "R":
                self.go_s(n)
                self.cur_dir = "S"
            elif dirs == "L":
                self.go_n(n)
                self.cur_dir = "N"

        elif self.cur_dir == "S":
            if dirs == "R":
                self.go_l(n)
                self.cur_dir = "W"
            elif dirs == "L":
                self.go_r(n)
                self.cur_dir = "E"

        elif self.cur_dir == "W":
            if dirs == "R":
                self.go_n(n)
                self.cur_dir = "N"
            elif dirs == "L":
                self.go_s(n)
                self.cur_dir = "S"

    def go_r(self, n):
        self.x += n

        # part 2
        for a in range(self.col + 1, self.col + n + 1):
            self.col = a
            self.visit()

    def go_l(self, n):
        self.x -= n

        # part 2
        for a in range(self.col - 1, self.col - n - 1, -1):
            self.col = a
            self.visit()

    def go_n(self, n):
        self.y += n

        # part 2
        for a in range(self.row - 1, self.row - n - 1, -1):
            self.row = a
            self.visit()

    def go_s(self, n):
        self.y -= n

        # part 2
        for a in range(self.row + 1, self.row + n + 1):
            self.row = a
            self.visit()

    def visit(self):
        if not self.answer2:
            if not self.board[self.row, self.col]:
                self.board[self.row, self.col] = True
            else:
                self.answer2 = abs(self.row - self.bsize1) + abs(self.col - self.bsize2)


if __name__ == "__main__":
    descrete_taxi = Taxi()

    inp = "R2, L5, L4, L5, R4, R1, L4, R5, R3, R1, L1, L1, R4, L4, L1, R4, L4, R4, L3, R5, R4, R1, R3, L1, L1, R1, L2, R5, L4, L3, R1, L2, L2, R192, L3, R5, R48, R5, L2, R76, R4, R2, R1, L1, L5, L1, R185, L5, L1, R5, L4, R1, R3, L4, L3, R1, L5, R4, L4, R4, R5, L3, L1, L2, L4, L3, L4, R2, R2, L3, L5, R2, R5, L1, R1, L3, L5, L3, R4, L4, R3, L1, R5, L3, R2, R4, R2, L1, R3, L1, L3, L5, R4, R5, R2, R2, L5, L3, L1, L1, L5, L2, L3, R3, R3, L3, L4, L5, R2, L1, R1, R3, R4, L2, R1, L1, R3, R3, L4, L2, R5, R5, L1, R4, L5, L5, R1, L5, R4, R2, L1, L4, R1, L1, L1, L5, R3, R4, L2, R1, R2, R1, R1, R3, L5, R1, R4"

    for step in inp.split():
        d = step[0]
        n = int(step[1:].split(',')[0])
        descrete_taxi.do_move(d, n)
        #print(descrete_taxi)

    print("Part1: {}".format(descrete_taxi.calc_dist()))
    print("Part2: {}".format(descrete_taxi.answer2))

    '''
    --- Day 1: No Time for a Taxicab ---

Santa's sleigh uses a very high-precision clock to guide its movements, and the clock's oscillator is regulated by stars. Unfortunately, the stars have been stolen... by the Easter Bunny. To save Christmas, Santa needs you to retrieve all fifty stars by December 25th.

Collect stars by solving puzzles. Two puzzles will be made available on each day in the advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!

You're airdropped near Easter Bunny Headquarters in a city somewhere. "Near", unfortunately, is as close as you can get - the instructions on the Easter Bunny Recruiting Document the Elves intercepted start here, and nobody had time to work them out further.

The Document indicates that you should start at the given coordinates (where you just landed) and face North. Then, follow the provided sequence: either turn left (L) or right (R) 90 degrees, then walk forward the given number of blocks, ending at a new intersection.

There's no time to follow such ridiculous instructions on foot, though, so you take a moment and work out the destination. Given that you can only walk on the street grid of the city, how far is the shortest path to the destination?

For example:

    Following R2, L3 leaves you 2 blocks East and 3 blocks North, or 5 blocks away.
    R2, R2, R2 leaves you 2 blocks due South of your starting position, which is 2 blocks away.
    R5, L5, R5, R3 leaves you 12 blocks away.

How many blocks away is Easter Bunny HQ?

Your puzzle answer was 239.
--- Part Two ---

Then, you notice the instructions continue on the back of the Recruiting Document. Easter Bunny HQ is actually at the first location you visit twice.

For example, if your instructions are R8, R4, R4, R8, the first location you visit twice is 4 blocks away, due East.

How many blocks away is the first location you visit twice?

Your puzzle answer was 141.
'''