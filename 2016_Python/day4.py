import re
import collections
import string

if __name__ == '__main__':

    with open('input4.txt') as f:
        total_sum=0

        real_names = []
        for line in f:
            s = re.search("(([a-z]+-?)+)+(\d+)(\[.+)",line)
            first = s.group(1)
            numb = s.group(3)
            checksum = s.group(4)

            count = collections.Counter(first.replace('-', ''))
            cnt = sorted(count.items(), key=lambda x: (-x[1], x[0]))[:5]
            checksum_predicted = "".join([a for a, let in cnt])
            checksum = "".join(checksum.replace("[", "").replace("]", ""))

            if checksum_predicted == checksum:
                real_names.append((first, numb))
                total_sum += int(numb)

            cipher=''
            alphabet = string.ascii_lowercase
            part2_ans=None
            for f, k in real_names:
                for c in f:
                    if c == "-":
                        cipher += " "
                    else:
                        cipher += alphabet[(alphabet.index(c) + int(k)) % (len(alphabet))]
                if cipher == "northpole object storage ":
                    part2_ans=k
                cipher=''

        print("Part1: {}".format(total_sum))
        print("Part2: {}".format(part2_ans))