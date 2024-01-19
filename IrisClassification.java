import org.apache.log4j.BasicConfigurator;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.io.ClassPathResource;

public class IrisClassification {

    public static void main(String[] args){
        BasicConfigurator.configure();
        loadData();
    }

    private static void loadData(){
        try(RecordReader recordReader = new CSVRecordReader(0, ',')) {
            recordReader.initialize(new FileSplit(
                    new ClassPathResource("iris.csv").getFile()));

            DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, 150, 4, 3);
            DataSet allData = iterator.next(150);
            allData.shuffle(123);

            SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.65);
            DataSet trainingData = testAndTrain.getTrain();
            DataSet testData = testAndTrain.getTest();

        } catch (Exception e) {
            System.out.println("Error:" + e.getLocalizedMessage());
        }
    }
}
