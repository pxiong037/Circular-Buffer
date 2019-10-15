# Circular-Buffer

This program creates a Producer thread that runs on a loop 100 times. During each
loop it waits a random 1 to 5 seconds before writing its loop count to the circularBuffer.
If the circularBuffer is full the Producer will wait 1 second and continue to wait until
the Consumer deletes a value in the circularBuffer to create space for the Producer to put
in its value. The program also contains a Consumer thread that runs until the Producer finishes 
its loop and the Consumer has deleted all values in the circularBuffer. The Consumer waits a random
2 to 4 seconds before reading the consumerIndex of the circularBuffer then writes it to the
output file and removes it from the circularBuffer. If there is no value in the circularBuffer
the Consumer will wait for 1 second and keep waiting until there's a value to read in the
circularBuffer. Once the Producer loop has finished it will inform the consumer that it has
finished and the consumer will then read, write and remove the last values in the circularBuffer
before it prints to the output file that it has finished.
