package Assignment3;
/**
 * Prechar Xiong
 * 09/30/19
 * ICS 462-01
 * Assignment 3
 * 
 * This program creates a Producer thread that runs on a loop 100 times. During each
 * loop it waits a random 1 to 5 seconds before writing its loop count to the circularBuffer.
 * If the circularBuffer is full the Producer will wait 1 second and continue to wait until
 * the Consumer deletes a value in the circularBuffer to create space for the Producer to put
 * in its value. The program also contains a Consumer thread that runs until the Producer finishes 
 * its loop and the Consumer has deleted all values in the circularBuffer. The Consumer waits a random
 * 2 to 4 seconds before reading the consumerIndex of the circularBuffer then writes it to the
 * output file and removes it from the circularBuffer. If there is no value in the circularBuffer
 * the Consumer will wait for 1 second and keep waiting until there's a value to read in the
 * circularBuffer. Once the Producer loop has finished it will inform the consumer that it has
 * finished and the consumer will then read, write and remove the last values in the circularBuffer
 * before it prints to the output file that it has finished.
 */
import java.util.Random;

public class Producer implements Runnable{
	/**
	 * All of these values are shared by both the Producer and Consumer.
	 * The circularBuffer is an Integer array of length 5.
	 * The producerIndex points to the next available slot in the circularBuffer
	 * The consumerIndex points to the last item removed from the circularBuffer
	 * The imDoneFlag tells when the Producer loop has finished.
	 */
	public static Integer[] circularBuffer = new Integer[5];
	public static int producerIndex = 0;
	public static int consumerIndex = 0;
	public static int imDoneFlag = 0;
	

	/**
	 * The run method is must be implemented by the consumer because of the Runnable
	 * interface.
	 */
	public void run() {
		int randomIntTime;
		int loopIterations = 100;
		int totalWaitTime = 0;
			try {
				for(int i = 0; i < loopIterations; i++) {
					randomIntTime = randomIntegerWithinRange(5,1);
					totalWaitTime += randomIntTime;
					Thread.sleep(randomIntTime * 1000);
					while(producerIndex - consumerIndex == Producer.circularBuffer.length) {
						System.out.println("Producer waiting for open spot");
						Thread.sleep(1000);
					}
					Producer.circularBuffer[Producer.producerIndex%5] = i;
					if(i == loopIterations-1) {
						Producer.imDoneFlag += 1;
					}
					printArray(Producer.circularBuffer);
					Producer.producerIndex += 1;
				}
				System.out.println("Producer waited: " + totalWaitTime);
			} catch(InterruptedException ie) {
				System.out.println("An error abrupted the application before it was able to finish. ");
			}
	}
	
	/**
	 * This method prints the circular buffer. This method was used in my testing 
	 * to make sure the program was working properly.
	 * @param array
	 */
	public void printArray(Integer[] array) {
		String s = "Producer Put in " + Producer.circularBuffer[Producer.producerIndex%5] +": [ ";
		String str = "";
		for(Integer x : array) {
			if(x == null) {
				str = "null";
			} else {
				str = x.toString();
			}
			s += str + " ";
		}
		s += "]";
		System.out.println(s);
	}
	
	/**
	 * Generates a random number within a range
	 * @param max value the random number can be
	 * @param min value the random number can be
	 * @return the random integer within the range
	 */
	public static int randomIntegerWithinRange(int max, int min) {
		Random random = new Random();
		int randomInt = random.nextInt((max-min) + 1) + min;
		return randomInt;
	}
	
	/**
	 * This driver executes the producer and consumer threads
	 */
	public static void main(String[] args) {
		Thread producerThread = new Thread(new Producer());
		Thread consumerThread = new Thread(new Consumer());
		producerThread.start();
		consumerThread.start();
	}
}
