# Problem 4 Part A

        .data
ping:   .asciiz "Ping\n"
pong:   .asciiz "Pong\n"
nl:     .asciiz "\n"

        .text
        .globl main
main:
        li   $s0, 96
        li   $s1, 6
        li   $s2, 8

loop:
        blez $s0, done

        div  $s0, $s1
        mfhi $t0
        div  $s0, $s2
        mfhi $t1

        bne  $t0, $zero, not6
        beq  $t1, $zero, next

        li   $v0, 4
        la   $a0, ping
        syscall
        j    next

not6:
        bne  $t1, $zero, number

        li   $v0, 4
        la   $a0, pong
        syscall
        j    next

number:
        li   $v0, 1
        move $a0, $s0
        syscall
        li   $v0, 4
        la   $a0, nl
        syscall

next:
        addi $s0, $s0, -1
        j    loop

done:
        li   $v0, 10
        syscall
