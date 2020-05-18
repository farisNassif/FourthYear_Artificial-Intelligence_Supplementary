package ie.gmit.sw;

import java.io.File;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationReLU;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationSoftMax;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.buffer.MemoryDataLoader;
import org.encog.ml.data.buffer.codec.CSVDataCODEC;
import org.encog.ml.data.buffer.codec.DataSetCODEC;
import org.encog.ml.data.folded.FoldedDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.cross.CrossValidationKFold;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.util.csv.CSVFormat;

import ie.gmit.sw.language.Language;
import ie.gmit.sw.util.Stopwatch;

/* 
 * Class that creates the NN topology, trains, tests and saves it.
 * Assisted with NN Development - https://s3.amazonaws.com/heatonresearch-books/free/Encog3Java-User.pdf 
 */
public class NeuralNetwork {

	public static void main(String[] args) {
		new NeuralNetwork();
	}

	public NeuralNetwork() {
		int inputs = 100;
		int outputs = 235;
		double minError = 0.005;

		/* Neural Network Configuration */
		BasicNetwork network = new BasicNetwork();
		/* Input layer, amount of nodes are equal to vector size */
		network.addLayer(new BasicLayer(new ActivationSoftMax(), true, inputs));
		/* Single hidden layer, nodes equal to sqrt of (input * output) nodes */
		network.addLayer(new BasicLayer(new ActivationSoftMax(), true, (int) Math.sqrt(inputs * outputs)));
		network.addLayer(new BasicLayer(new ActivationSoftMax(), true, (int) Math.sqrt(inputs * outputs)));
		/* Output layer, size equal to amount of languages to be classified (235) */
		network.addLayer(new BasicLayer(new ActivationSoftMax(), false, outputs));
		network.getStructure().finalizeStructure();
		network.reset();

		/* Handle on the CSV file */
		DataSetCODEC dsc = new CSVDataCODEC(new File("./data.csv"), CSVFormat.DECIMAL_POINT, false, inputs, outputs,
				false);

		MemoryDataLoader mdl = new MemoryDataLoader(dsc);
		/* Configure the training set */
		MLDataSet mdlTrainingSet = mdl.external2Memory();
		/* "folds" the data into several equal (or nearly equal) datasets */
		FoldedDataSet folded = new FoldedDataSet(mdlTrainingSet);

		/* Neural Network Training configu */
		MLTrain train = new ResilientPropagation(network, folded);

		/* (5)k-fold cross validation */
		CrossValidationKFold cv = new CrossValidationKFold(train, 5);

		Stopwatch timer = new Stopwatch();

		/* Train */
		int epoch = 0;
		System.out.println("[INFO] Training...");
		timer.start();
		do {
			cv.iteration();
			epoch++;
			System.out.println("Epoch: " + epoch + " Error Rate: " + cv.getError() + " ...");

		} while (cv.getError() > minError);
		/* Declare the end of training */
		cv.finishTraining();
		timer.stop();

		System.out.println(
				"Training Done in " + epoch + " epochs with error rate " + cv.getError() + " in " + timer.toString());

		double[] in = { 0, 0, 0, 0, 0, 0, 0.5, 0.5, 0.5, 1, 0, 0, 0, 0.5, 0, 0, 0.5, 0, 0.5, 0, 0, 0, 0, 0.5, 0.5, 0, 0,
				0, 0, 0, 0, 0, 0, 0.5, 0, 0, 0, 0, 0.5, 0.5, 0, 0, 0, 0, 0, 0.5, 0, 0, 0, 0, 0, 0, 0.5, 0.5, 0, 0, 0.5,
				0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0.5, 0.5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0.5, 0,
				0.5, 0, 0, 0, 0, 0, 0.5, 0, 0, 0, 0.5 };

		double[] out = new double[235];

		/* Stop Encog running */
		Encog.getInstance().shutdown();
	}
}