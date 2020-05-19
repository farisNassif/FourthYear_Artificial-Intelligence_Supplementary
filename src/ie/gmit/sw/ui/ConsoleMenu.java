package ie.gmit.sw.ui;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import ie.gmit.sw.NeuralNetwork;
import ie.gmit.sw.process.VectorProcessor;

/* Simple class that just handles the programs menus */
public class ConsoleMenu {
	Scanner scanner = new Scanner(System.in);

	public void MainMenu() {
		int option;
		boolean loop = true;
		System.out.println("---------- Main Menu ----------");
		System.out.println("Press 1 : Neural Network Options");
		System.out.println("Press 2 : Vector Hashing / Ngram Options");
		System.out.println("Press 3 : Classification Options");
		System.out.println("Press 9 : Quit");
		option = scanner.nextInt();

		while (loop == true) {
			switch (option) {
			case 1:
				NeuralNetworkMenu();
				break;
			case 2:
				VectorMenu();
				break;
			case 3:
				System.out.println("Classification Opts");
				break;
			case 9:
				loop = false;
				System.out.println("\n*Quitting Program ...");
				break;
			default:
				System.out.println("Invalid input, try again.");
			}

			if (option != 9) {
				System.out.println("\n---------- Main Menu ----------");
				System.out.println("Press 1 : Neural Network Options");
				System.out.println("Press 2 : Vector Hashing / Ngram Options");
				System.out.println("Press 3 : Classification Options");
				System.out.println("Press 9 : Quit");
				option = scanner.nextInt();
			}

		}
		scanner.close();
	}

	/* Neural Network Related Items */
	public void NeuralNetworkMenu() {
		/* Params for NN */
		int inputNodes = 0;
		int save = 0;

		int option;
		boolean loop = true;
		System.out.println("\n------ Neural Network Menu ------");
		System.out.println("Press 1 : Create and Train a Neural Network on the fly (And optional save)");
		System.out.println("Press 2 : Neural Network Option 2");
		System.out.println("Press 3 : Neural Network Option 3");
		System.out.println("Press 9 : Go back to the Main Menu");
		option = scanner.nextInt();

		while (loop == true) {
			switch (option) {
			case 1:
				System.out.println("How many input nodes should the NN have? (Should be equal to vector size)");
				inputNodes = scanner.nextInt();
				System.out.println("Save: Press 1 | Otherwise : Press 0");
				save = scanner.nextInt();
				new NeuralNetwork(save, inputNodes);
				break;
			case 2:
				System.out.println("Neural Network Option 2");
				break;
			case 3:
				System.out.println("Neural Network Option 3");
				break;
			case 9:
				loop = false;
				System.out.println("Going Back ...");
				break;
			default:
				System.out.println("Invalid input, try again.");
			}

			if (option != 9) {
				System.out.println("\n------ Neural Network Menu ------");
				System.out.println("Press 1 : Create and Train a Neural Network on the fly (And optional save)");
				System.out.println("Press 2 : Neural Network Option 2");
				System.out.println("Press 3 : Neural Network Option 3");
				System.out.println("Press 9 : Go back to the Main Menu");
				option = scanner.nextInt();
			}
		}
	}

	/* Vector Related Items */
	public void VectorMenu() {
		/* Params for vector Processor */
		int vectorSize;
		int kmers;

		int option;
		boolean loop = true;
		System.out.println("\n----- Vector / Ngram Menu -----");
		System.out.println("Press 1 : Create a new Vector & specify sizes");
		System.out.println("Press 2 : Vector / Ngram Option 2");
		System.out.println("Press 3 : Vector / Ngram Option 3");
		System.out.println("Press 9 : Go back to the Main Menu");
		option = scanner.nextInt();

		while (loop == true) {
			switch (option) {
			case 1:
				System.out.println("Specify Max Vector Size (Ideally 125 - 200)");
				vectorSize = scanner.nextInt();
				System.out.println("K-mer size (1 / 2 / 3 / 4, Anything else may or may not crash everything)");
				option = scanner.nextInt();
				kmers = option;

				new VectorProcessor(kmers, vectorSize);
				break;
			case 2:
				System.out.println("Vector / Ngram Option 2");
				break;
			case 3:
				System.out.println("Vector / Ngram Option 3");
				break;
			case 9:
				loop = false;
				System.out.println("Going Back ...");
				break;
			default:
				System.out.println("Invalid input, try again.");
			}

			if (option != 9) {
				System.out.println("\n----- Vector / Ngram Menu -----");
				System.out.println("Press 1 : Create a new Vector & specify sizes");
				System.out.println("Press 2 : Vector / Ngram Option 2");
				System.out.println("Press 3 : Vector / Ngram Option 3");
				System.out.println("Press 9 : Go back to the Main Menu");
				option = scanner.nextInt();
			}
		}
	}
}