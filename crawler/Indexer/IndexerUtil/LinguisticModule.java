package Indexer.IndexerUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by rbtlong on 5/13/15.
 */
public class LinguisticModule {
    static BinSearchList strDict = new BinSearchList();
    static PortersSuffix[] suffixes = new PortersSuffix[]{
            new PortersSuffix("sses", "ss"),
            new PortersSuffix("ies", "i"),
            new PortersSuffix("ational", "ate"),
            new PortersSuffix("tional", "tion")
    };

    public static String process(String inp) {
        return inp;
/*        if (strDict.size() == 0)
            loadDictionary();

        String value = inp;
        int dictIdx = strDict.indexOfIgnoreCase(inp.toLowerCase());
        if (dictIdx != -1) {
            if (!allCapitalized(inp)
                    && !allCapitalized(strDict.get(dictIdx)))
                value = inp.toLowerCase();
            value = applyPortersRule(strDict.get(dictIdx));
        }
        return value;*/
    }

    private static String applyPortersRule(String inp) {
        if (allCapitalized(inp)) return inp; // ignore all capitals case
        String value = inp;

        for (PortersSuffix sfx : suffixes) {
            if (inp.length() > sfx.getSuffixSeek().length()
                    && inp.toLowerCase().endsWith(sfx.getSuffixSeek()))
                value = inp.replace(
                        sfx.getSuffixSeek(),
                        sfx.getSuffixReplace());
        }
        return value;
    }

    private static boolean allCapitalized(String inp) {
        for (int i = 0; i < inp.length(); ++i)
            if (Character.isLowerCase(inp.charAt(i)))
                return false;
        return true;
    }

    private static void loadDictionary() {
        try {
            FileInputStream fis = new FileInputStream(new File("dict.txt"));
            String token = "";
            while (fis.available() > 0) {
                char ch = (char) fis.read();
                if (ch == '\n' && token.length() > 0) {
                    strDict.add(token);
                    token = "";
                } else token += ch;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
