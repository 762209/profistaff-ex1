package filecombiner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class provides simple way of combining text files
 * @author Kambachekov Murat
 * @version 1.1
 */
public class FileCombiner {
	/** This field represent root folder where files reads*/
	private Path rootFolder;
	/** This field represent result folder where persists combined file*/
	private Path resultFolder;
	
	/** Constructor with default parameters. Creates folders in the project folder*/
	public FileCombiner() {
		rootFolder = Paths.get("default_root").toAbsolutePath();
		resultFolder = Paths.get("default_result").toAbsolutePath();
	}
	/**
	 * Constructor with custom parameters
	 * @param rootFolder - root folder where files reads
	 * @param resultFolder - folder where persists combined file
	 * @param charset - charset of readable files
	 */
	public FileCombiner(Path rootFolder, Path resultFoler) {
		this.rootFolder = rootFolder;
		this.resultFolder = resultFoler;
	}
	
	/**
	 * Field value retrieval method {@link FileCombiner#rootFolder}
	 * @return root folder
	 */
	public Path getRootFolder() {
		return rootFolder;
	}
	
	/**
	 * Field value determination method {@link FileCombiner#rootFolder}
	 * @param rootFolder - root folder
	 */
	public void setRootFolder(Path rootFolder) {
		this.rootFolder = rootFolder;
	}
	
	/**
	 * Field value retrieval method {@link FileCombiner#resultFolder}
	 * @return result folder
	 */
	public Path getResultFolder() {
		return resultFolder;
	}
	
	/**
	 * Field value determination method {@link FileCombiner#resultFolder}
	 * @param resultFolder - result folder
	 */
	public void setResultFolder(Path resultFolder) {
		this.resultFolder = resultFolder;
	}
	/**
	 * This method combines the content of files into one file
	 * based on the parameters specified in the constructor
	 */
	public void combineFilesContent() {
		try (Stream<Path> entries = Files.walk(rootFolder)) {
			List<Path> fileList = entries.filter(Files::isRegularFile)
									 	 .sorted(Comparator.comparing(Path::getFileName))
									 	 .collect(Collectors.toList());
			Path resultFilePath = resultFolder.resolve("result.txt");
			Files.deleteIfExists(resultFilePath);
			for (Path path : fileList) 
				Files.write(resultFilePath, Files.readAllBytes(path),StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
