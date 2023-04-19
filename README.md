# AutosarFileReorderer

AutosarFileReorderer is a Java program that reorders the `CONTAINER` elements in an AUTOSAR XML file alphabetically by their `SHORT-NAME` attribute. The program reads an input AUTOSAR XML file, sorts its `CONTAINER` elements, creates a new AUTOSAR XML document with the sorted elements, and writes the new document to an output file.

## Requirements

To run AutosarFileReorderer, you need to have the following software installed on your system:

- Java SE Development Kit 8 or later
- An AUTOSAR XML file to reorder

## Usage

To run AutosarFileReorderer, you need to open a terminal or command prompt, navigate to the directory where the program's JAR file is located, and run the following command:

```
java -jar AutosarFileReorderer.jar <input_file_name>
```

Replace `<input_file_name>` with the name of the input AUTOSAR XML file to reorder. The program will create a new AUTOSAR XML file with the same content as the input file, but with the `CONTAINER` elements sorted alphabetically by their `SHORT-NAME` attribute. The new file will have the same name as the input file, with the suffix `_mod` added before the file extension.

```

## Credits

The program uses the following open-source libraries:

- Java XML APIs (javax.xml)
- Apache Xerces2 Java Parser
- Apache Xalan Java Transformer
