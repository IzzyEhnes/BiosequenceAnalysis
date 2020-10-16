import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;



class AminoAcid
{
    private char aminoAcid;



    public AminoAcid()
    {

    }



    public AminoAcid(char inChar)
    {
        aminoAcid = inChar;
    }
}






class Peptide
{
    private String peptide;



    public Peptide()
    {

    }



    public Peptide(String inString)
    {
        peptide = inString;
    }



    public void setPeptide(String inString)
    {
        peptide = inString;

    }



    public String getPeptide()
    {
        return peptide;
    }






    public HashMap<Protein, ArrayList<String>> findPotentialMatches(ArrayList<Protein> inList)
    {
        HashMap<Protein, ArrayList<String>> matches = new HashMap<Protein, ArrayList<String>>();

        String inPeptide = this.peptide;

        int peptideLength = peptide.length();

        String proteinSubstring = "";

        int row = 1;
        for (Protein currentProtein : inList)
        {
            row++;


            int beginningIndex = 0;
            int endIndex = 0;

            boolean match = false;
            for (int j = 0; j < currentProtein.getLength() && match == false; j++)
            {
                if (currentProtein.getProtein().charAt(j) == inPeptide.charAt(0))
                {
                    endIndex = j + peptideLength;

                    if (endIndex > currentProtein.getLength())
                    {
                        endIndex = currentProtein.getLength();
                    }

                    proteinSubstring = currentProtein.getProtein().substring(j, endIndex);

                    //System.out.println("Protein substring");
                    //System.out.println(proteinSubstring);

                    match = isMatch(peptide, proteinSubstring);

                    beginningIndex = j;
                }
            }

            if (match)
            {
                ArrayList<String> proteinData = new ArrayList<String>();

                proteinData.add(peptide);
                proteinData.add(Integer.toString(row));
                proteinData.add(Integer.toString(beginningIndex));
                proteinData.add(Integer.toString(endIndex));

                matches.put(currentProtein, proteinData);
            }
        }

        return matches;
    }






    public boolean isMatch(String peptide, String proteinSubstring)
    {
        if (peptide.equals(proteinSubstring))
        {
            return true;
        }

        else
        {
            return false;
        }
    }



    @Override
    public String toString()
    {
        return peptide;
    }
}






class Protein
{
    private String protein;



    public Protein()
    {

    }



    public Protein(String inString)
    {
        protein = inString;
    }



    public void setProtein(String inString)
    {
        protein = inString;
    }



    public String getProtein()
    {
        return protein;
    }



    public int getLength()
    {
        return protein.length();
    }



    @Override
    public String toString()
    {
        return protein;
    }
}






public class Main
{
    static class CSV
    {
        public static void writeToCSV(HashMap<Protein, ArrayList<String>> inMap)
        {
            try
            {
                File csv = new File("/home/izzy/Desktop/Repos/BiosequenceAnalysis/output.csv");

                csv.createNewFile();

                FileWriter csvWriter = new FileWriter(csv);

                csvWriter.append("PROTEIN").append(',');
                csvWriter.append("PEPTIDE").append(',');
                csvWriter.append("ROW").append(',');
                csvWriter.append("BEGINNING INDEX").append(',');
                csvWriter.append("END INDEX").append('\n');

                for (Protein p : inMap.keySet())
                {
                    csvWriter.append(p.getProtein()).append(',');

                    ArrayList<String> list = inMap.get(p);

                    for (String s : list)
                    {
                        csvWriter.append(s).append(',');
                    }

                    csvWriter.append('\n');
                }

                csvWriter.flush();
                csvWriter.close();
            }

            catch (IOException e)
            {
                System.out.println("Error creating or editing file: ");
                e.printStackTrace();
            }
        }
    }

    public static void printProteinList(ArrayList<Protein> inList)
    {
        for (Protein p : inList)
        {
            System.out.println(p);
        }
    }



    public static void printHashMap(HashMap<Protein, ArrayList<String>> inMap)
    {
        for (Protein p : inMap.keySet())
        {
            System.out.print("\nProtein: ");
            System.out.print(p.toString());

            System.out.print("\nPeptide found: ");
            System.out.print(inMap.get(p).get(0));

            System.out.print("\nRow in original CSV: ");
            System.out.print(inMap.get(p).get(1));

            System.out.print("\nBeginning index: ");
            System.out.print(inMap.get(p).get(2));

            System.out.print("\nEnd index: ");
            System.out.print(inMap.get(p).get(3));

            System.out.println();
        }
    }



    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter a peptide: ");
        Peptide targetPeptide = new Peptide(scanner.nextLine());

        System.out.println(targetPeptide);

        System.out.print("Please enter a filename: ");
        String fileName = scanner.nextLine();

        scanner.close();
        scanner = null;



        Scanner fileReader = null;
        // Try opening file fileName
        try
        {
            fileReader = new Scanner(new File(fileName));
        }

        // If file cannot be opened, display error and exit program
        catch (FileNotFoundException fileError)
        {
            System.out.println(String.format("There was a problem opening file \"%s\": error = %s", fileName, fileError.getMessage()));

            System.out.println("Exiting program...");

            System.exit(1);
        }



        // Read CSV file and add proteins to an ArrayList
        ArrayList<Protein> proteinList = new ArrayList();
        while (fileReader.hasNextLine())
        {
            String line = fileReader.nextLine().trim();

            // If the row is column names or is empty, skip it
            if (line.charAt(0) == '#' || line.length() == 0)
            {
                continue;
            }

            else
            {
                Protein p = new Protein(line);
                proteinList.add(p);
            }
        }

        //printProteinList(proteinList);

        HashMap<Protein, ArrayList<String>> matches = new HashMap<Protein, ArrayList<String>>();

        matches = targetPeptide.findPotentialMatches(proteinList);

        //printHashMap(matches);
        
        CSV.writeToCSV(matches);

    }
}
