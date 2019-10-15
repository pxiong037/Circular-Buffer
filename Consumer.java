package Assignment3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Consumer implements Runnable{

	/**
	 * The run method is must be implemented by the consumer because of the Runnable
	 * interface.
	 */
	public void run() {
		int randomIntTime;
		int totalWaitTime = 0;
		createFile();
		try {
			while(Producer.imDoneFlag == 0 || !isEmpty(Producer.circularBuffer)) {
				randomIntTime = Producer.randomIntegerWithinRange(4, 2);
				totalWaitTime += randomIntTime;
				Thread.sleep(randomIntTime * 1000);
				while(Producer.consumerIndex == Producer.producerIndex && Producer.imDoneFlag != 1) {
					printToFile("Consumer waiting");
					totalWaitTime += 1;
					Thread.sleep(1000);
				}
				printAndRemove();
			}
			printToFile("Consumer done");
			System.out.println("Consumer waited: " + totalWaitTime);
		} catch(InterruptedException ie) {
			System.out.println("An error abrupted the application before it was able to finish. ");
		} 
	}
	
	/**
	 * This method prints to the file and removes the Integer at the 
	 * consumerIndex in the circularBuffer and increases the consumerIndex
	 * by one.
	 */
	public void printAndRemove() {
		int index = Producer.consumerIndex%5;
		Integer removedNum = Producer.circularBuffer[index];
		printToFile(removedNum.toString());
		Producer.circularBuffer[index] = null;
		printArray(Producer.circularBuffer, removedNum);
		Producer.consumerIndex += 1;
	}

	/**
	 * This methods creates an output.txt file if it doesn't already exist, to output
	 * the numbers read by the consumer, output when the consumer waits and when the consumer
	 * finishes.
	 */
	public void createFile() {
		try {
			FileWriter fileWriter;
			File file = new File("output.txt");
			PrintWriter printWriter;
			if(file.exists()) {
				fileWriter = new FileWriter(file.getAbsoluteFile(), true);
				printWriter = new PrintWriter(fileWriter);
			} else {
				fileWriter = new FileWriter(file);
				printWriter = new PrintWriter(fileWriter);
				printWriter.println("Prechar Xiong \nICS 462 Assignment #3 \n");
			}
			printWriter.close();
		}	catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method takes in a string and prints it to the output file
	 * @param msg is the String to be printed to the file
	 */
	public void printToFile(String msg) {
		try {
			File file = new File("output.txt");
			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(msg);
			printWriter.close();
		}	catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method prints the array to the console, This was used for testing purposes to make sure
	 * the program was working properly
	 * @param array
	 * @param removedNum
	 */
	public void printArray(Integer[] array, Integer removedNum) {
		String s = "Consumer Pulled out " + removedNum +": [ ";
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
	 * this method checks to see if the Integer array is empty
	 * @param array
	 * @return if the array is empty it returns otherwise it returns false
	 */
	public boolean isEmpty(Integer[] array) {
		boolean empty = false;
		int nullCounter = 0;
		for(Integer x : array) {
			if(x == null) {
				nullCounter++;
			}
		}
		if(nullCounter == 5) {
			empty = true;
		}
		return empty;
	}
}
