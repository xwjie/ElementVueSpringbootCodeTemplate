package cn.xiaowenjie.tool;

import cn.xiaowenjie.chartbeans.EndData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReadLog2CSV {

    public static List<String> readFile2List(InputStream fileInputStream,
                                             String charset) throws IOException {
        List<String> sb = new ArrayList<String>();

        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(fileInputStream, charset));

            String s = null;

            while ((s = br.readLine()) != null) {
                sb.add(s);
            }
        } finally {
            if (br != null) {

                br.close();
            }
        }

        return sb;
    }

    public static List<EndData> deal(File file) throws IOException {
        List<String> list = readFile2List(new FileInputStream(file), "Cp1252");

        return list.stream().map(s -> s.split(",")).filter(arr -> arr.length == 2)
                .map(ReadLog2CSV::toData).collect(Collectors.toList());
    }

    public static EndData toData(String[] arr) {
        EndData data = new EndData();

        data.setDate(arr[0].trim());
        data.setVolume(Integer.parseInt(arr[1].trim()));

        return data;
    }

}
