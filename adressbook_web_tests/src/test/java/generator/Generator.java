package generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import common.CommonFunctions;
import model.ContactData;
import model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Generator {

    @Parameter(names = {"--type", "-t"})
    String type;

    @Parameter(names = {"--output", "-o"})
    String output;

    @Parameter(names = {"--format", "-f"})
    String format;

    @Parameter(names = {"--count", "-n"})
    int count;

    public static void main(String[] args) throws IOException {
        var generator = new Generator();
        JCommander.newBuilder()
                .addObject(generator)
                .build()
                .parse(args);
        generator.run();
    }

    private void run() throws IOException {
        var data = generate();
        save(data);
    }

    private Object generate() {
        if ("groups".equals(type)) {
            return generateGroups();
        } else if ("contacts".equals(type)) {
            return generateContacts();
        } else {
            throw new IllegalArgumentException("Неизвестный тип данных: " + type);
        }
    }

    private Object generateData(Supplier<Object> dataSupplier){
        return Stream.generate(dataSupplier).limit(count).collect(Collectors.toList());
    }

    private Object generateGroups() {
        return generateData(() -> new GroupData()
                .withName(CommonFunctions.randomString(10))
                .withHeader(CommonFunctions.randomString(10))
                .withFooter(CommonFunctions.randomString(10)));
   }

    private Object generateContacts() {
        return generateData(() -> new ContactData()
                .withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(10)));
    }

    /*private Object generateContacts() {
        var result = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            result.add(new ContactData(
                    "", // id
                    CommonFunctions.randomString(i * 5), // firstName
                    CommonFunctions.randomString(i * 5), // middleName
                    CommonFunctions.randomString(i * 5), // lastName
                    CommonFunctions.randomString(i * 5), // nickname
                    "", // photo
                    CommonFunctions.randomString(i * 5), // title
                    CommonFunctions.randomString(i * 5), // company
                    CommonFunctions.randomString(i * 10), // address
                    CommonFunctions.randomPhone(), // homePhone
                    CommonFunctions.randomPhone(), // mobilePhone
                    CommonFunctions.randomPhone(), // workPhone
                    CommonFunctions.randomPhone(), // fax
                    CommonFunctions.randomEmail(), // email
                    CommonFunctions.randomEmail(), // email2
                    CommonFunctions.randomEmail(), // email3
                    "https://" + CommonFunctions.randomString(5) + ".com", // homepage
                    String.valueOf(i % 28 + 1), // birthdayDay
                    "January", // birthdayMonth
                    String.valueOf(1980 + i % 20), // birthdayYear
                    String.valueOf(i % 28 + 1), // anniversaryDay
                    "February", // anniversaryMonth
                    String.valueOf(2000 + i % 20) // anniversaryYear
            ));
        }
        return result;
    }*/


    private void save(Object data) throws IOException {
        if ("json".equals(format)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            var json = mapper.writeValueAsString(data);

            try (var writer = new FileWriter(output)) {
                writer.write(json);
            }
        } else if ("yaml".equals(format)) {
            var mapper = new YAMLMapper();
            mapper.writeValue(new File(output), data);
        } else if ("xml".equals(format)) {
            var mapper = new XmlMapper();
            mapper.writeValue(new File(output), data);
        } else {
            throw new IllegalArgumentException("Неизвестный формат данных: " + format);
        }
    }
}
