import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GenericXML {
    Map<String, Element> allElements;

    String genericXML(String[] elementSpecifications) {
        allElements = new HashMap<>();

        for (String elementSpec : elementSpecifications) {

            // spliter-ko aloha ilay spec
            String[] parts = elementSpec.split("/");

            Element child = fetchElement(parts[0]); // parts[0] ny name an'ny element en cours
            Element parent = fetchElement(parts[1]); // parts[1] ny parent-ny

            // raha manana parent izy, izany hoe tsy "root" dia ajouter-ko ao amin'ny
            // children an'io parent io izy
            if (parent != null) {
                parent.children.add(child);
            }

            // raha manana attribut izy dia ajouter-ko anaty liste d'attributs
            if (parts.length > 2) {
                child.attributes.addAll(Arrays.asList(parts[2].split(",")));
            }
        }

        StringBuilder result = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?>");

        // Manomboka ny recursion ary ny root no voalohany
        append(allElements.get("root"), result);

        return result.toString();
    }

    Element fetchElement(String elementName) {

        if (elementName.equals("-")) {
            return null;
        }

        if (!allElements.containsKey(elementName)) {
            allElements.put(elementName, new Element(elementName));
        }

        return allElements.get(elementName);
    }

    // ity ny fonction recursive
    void append(Element element, StringBuilder result) {
        result.append("<");
        result.append(element.name);

        // eto no mametaka ny attributs-ny
        for (String attr : element.attributes) {
            result.append(" ");
            result.append(attr);
            result.append("=''");
        }

        result.append(">");

        for (Element child : element.children) {
            // recursive call ho an'ny child elemens rehetra
            append(child, result);
        }

        result.append("</");
        result.append(element.name);
        result.append(">");
    }
}

class Element {
    String name;
    List<String> attributes;
    List<Element> children;

    Element(String elementName) {
        this.name = elementName;
        this.attributes = new ArrayList<>();
        this.children = new ArrayList<>();
    }
}
