# Problem 3 Part A
main:   addi $s0, $zero, 9216
        addi $s1, $zero, 0
        addi $s2, $zero, 4
        j    test
loop:   sll  $t0, $s1, 2
        add  $t0, $s0, $t0
        lw   $t1, 0($t0)
        lw   $t2, 4($t0)
        bne  $t1, $t2, else
        add  $t2, $t2, $t1
        sw   $t2, 4($t0)
        j    next
else:   lw   $t3, 20($s0)
        add  $t3, $t3, $t2
        sw   $t3, 20($s0)
next:   addi $s1, $s1, 1
test:   bne  $s1, $s2, loop
spin:   j    spin
# 00000000000000000000000000000000
