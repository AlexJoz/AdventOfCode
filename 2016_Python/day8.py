import numpy as np

class Screen:
    def __init__(self):
        self.screen = np.zeros((6, 50))

    def create_rect(self, size):
        self.screen[0:size[1], 0:size[0]] = 1

    def show(self):
        print(self.screen)

    def shiftCol(self, col, size):
        self.screen[:, col] = np.roll(self.screen[:, col], shift=size)

    def shiftRow(self, row, size):
        self.screen[row, :] = np.roll(self.screen[row, :], shift=size)

if __name__ == '__main__':

    display = Screen()

    with open('input8.txt') as f:
        a = f.read().splitlines()

    for line in a:

        if line.startswith("rect"):
            command, size = line.split()
            display.create_rect((tuple(map(int, size.split("x")))))

        elif line.startswith("rotate column x="):
            display.shiftCol(*map(int, line.replace("rotate column x=", "").split("by")))

        elif line.startswith("rotate row y="):
            display.shiftRow(*map(int, line.replace("rotate row y=", "").split("by")))

    print("Part1: ", np.sum(display.screen))

    for a in range(10):
        print(display.screen[:6, a * 5:(a + 1) * 5])
        print(" ")

    "Ans2 = UPOJFLBCEZ"


'''
--- Day 8: Two-Factor Authentication ---

You come across a door implementing what you can only assume is an implementation of two-factor authentication after a long game of requirements telephone.

To get past the door, you first swipe a keycard (no problem; there was one on a nearby desk). Then, it displays a code on a little screen, and you type that code on a keypad. Then, presumably, the door unlocks.

Unfortunately, the screen has been smashed. After a few minutes, you've taken everything apart and figured out how it works. Now you just have to work out what the screen would have displayed.

The magnetic strip on the card you swiped encodes a series of instructions for the screen; these instructions are your puzzle input. The screen is 50 pixels wide and 6 pixels tall, all of which start off, and is capable of three somewhat peculiar operations:

    rect AxB turns on all of the pixels in a rectangle at the top-left of the screen which is A wide and B tall.
    rotate row y=A by B shifts all of the pixels in row A (0 is the top row) right by B pixels. Pixels that would fall off the right end appear at the left end of the row.
    rotate column x=A by B shifts all of the pixels in column A (0 is the left column) down by B pixels. Pixels that would fall off the bottom appear at the top of the column.

For example, here is a simple sequence on a smaller screen:

    rect 3x2 creates a small rectangle in the top-left corner:

    ###....
    ###....
    .......

    rotate column x=1 by 1 rotates the second column down by one pixel:

    #.#....
    ###....
    .#.....

    rotate row y=0 by 4 rotates the top row right by four pixels:

    ....#.#
    ###....
    .#.....

    rotate column x=1 by 1 again rotates the second column down by one pixel, causing the bottom pixel to wrap back to the top:

    .#..#.#
    #.#....
    .#.....

As you can see, this display technology is extremely powerful, and will soon dominate the tiny-code-displaying-screen market. That's what the advertisement on the back of the display tries to convince you, anyway.

There seems to be an intermediate check of the voltage used by the display: after you swipe your card, if the screen did work, how many pixels should be lit?

Your puzzle answer was 116.
--- Part Two ---

You notice that the screen is only capable of displaying capital letters; in the font it uses, each letter is 5 pixels wide and 6 tall.

After you swipe your card, what code is the screen trying to display?

Your puzzle answer was UPOJFLBCEZ.
'''