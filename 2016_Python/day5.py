import hashlib

if __name__ == '__main__':
    i = 0
    PASS = ""
    Z = 0
    while (Z<8) :
        m = hashlib.md5()
        m.update(("ffykfhsq" + str(i)).encode('utf-8'))
        i += 1
        fivezeroeshash = m.hexdigest()
        if str(fivezeroeshash[:5]) == '00000':
            PASS += (fivezeroeshash[5])
            Z+=1
            print(PASS)
    #PART 2
    position_tracking = ''
    arra = ['&', '&', '&', '&', '&', '&', '&', '&']
    while (arra.count('&') != 0):
        m = hashlib.md5()
        m.update(("ffykfhsq" + str(i)).encode('utf-8'))
        i += 1
        five_zeroes_hash = m.hexdigest()

        if str(five_zeroes_hash[:5]) == '00000':
            position = five_zeroes_hash[5]
            if (position_tracking.count(position) == 0):
                try:
                    arra[int(position)] = five_zeroes_hash[6]
                    position_tracking += position
                except:
                    pass

            Z += 1
            print("".join(arra))
