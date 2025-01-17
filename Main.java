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




    public int length()
    {
        return this.peptide.length();
    }



    public int findMax(int a, int b)
    {
        if (a > b)
        {
            return a;
        }

        else
        {
            return b;
        }
    }




    public HashMap<Protein, ArrayList<String>> findPotentialMatches(ArrayList<Protein> inList)
    {
        HashMap<Protein, ArrayList<String>> matches = new HashMap<Protein, ArrayList<String>>();

        String inPeptide = this.peptide;

        int peptideLength = peptide.length();

        String proteinSubstring = "";

        ArrayList<Peptide> peptideList = new ArrayList<>();

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



    public void getLongestCommonSubsequence(Peptide targetPeptide, Peptide inPeptide)
    {
        System.out.println(targetPeptide);
        System.out.println(inPeptide);

        int m = targetPeptide.length();
        int n = inPeptide.length();

        int[][] L = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++)
        {
            for (int j = 0; j <= n; j++)
            {
                if (i == 0 || j == 0)
                {
                    L[i][j] = 0;
                }

                else if (targetPeptide.peptide.charAt(i - 1) == inPeptide.peptide.charAt(j - 1))
                {
                    L[i][j] = L[i - 1][j - 1] + 1;

                }

                else
                {
                    L[i][j] = findMax(L[i - 1][j], L[i][j - 1]);
                }
            }
        }

        int index = L[m][n];
        int temp = index;

        char[] LCS = new char[index + 1];
        LCS[index] = '\n'; // set terminating char

        int i = m;
        int j = n;
        while (i > 0 && j > 0)
        {
            if (targetPeptide.peptide.charAt(i - 1) == inPeptide.peptide.charAt(j - 1))
            {
                LCS[index - 1] = targetPeptide.peptide.charAt(i - 1);

                i--;
                j--;
                index--;
            }

            else if (L[i - 1][j] > L[i][j - 1])
            {
                i--;
            }

            else
            {
                j--;
            }
        }

        System.out.println("LCS of " + targetPeptide.peptide + " and " + inPeptide.peptide + " is ");
        for (int k = 0; k <= temp; k++)
        {
            System.out.print(LCS[k]);
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



    public int getNumberOfPeptides()
    {
        Protein inProtein = new Protein();
        inProtein = this;

        int count = 1;

        for (int i = 0; i < inProtein.getLength(); i++)
        {
            if (inProtein.protein.charAt(i) == ' ')
            {
                count++;
            }
        }

        return count;
    }



    public ArrayList<Peptide> getPeptidesInProtein()
    {
        Protein inProtein = new Protein();
        inProtein = this;

        int length = inProtein.getNumberOfPeptides();

        ArrayList<Peptide> peptideList = new ArrayList<>();

        StringBuilder peptideBuilder = new StringBuilder();

        for (int i = 0; i < inProtein.getLength(); i++)
        {
            peptideBuilder.append(inProtein.protein.charAt(i));

            if (inProtein.protein.charAt(i) == ' ' || i == inProtein.getLength() - 1)
            {
                Peptide temp = new Peptide(peptideBuilder.toString());
                peptideList.add(temp);
                peptideBuilder.setLength(0);
            }
        }

        return peptideList;
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
        public static ArrayList<Protein> readCSV(String inFile)
        {
            Scanner fileReader = null;
            // Try opening file fileName
            try
            {
                fileReader = new Scanner(new File(inFile));
            }

            // If file cannot be opened, display error and exit program
            catch (FileNotFoundException fileError)
            {
                System.out.println(String.format("There was a problem opening file \"%s\": error = %s", inFile, fileError.getMessage()));

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

            return proteinList;
        }



        public static void writeCSV(HashMap<Protein, ArrayList<String>> inMap)
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

        Peptide a = new Peptide("GILFPAILAK");
        Peptide b = new Peptide("AL");

        a.getLongestCommonSubsequence(a, b);


        /*
        System.out.print("Please enter a peptide: ");
        Peptide targetPeptide = new Peptide(scanner.nextLine());

        System.out.println(targetPeptide);

        System.out.print("Please enter a filename: ");
        String fileName = scanner.nextLine();

        scanner.close();
        scanner = null;



        ArrayList<Protein> proteinList = new ArrayList<Protein>();
        proteinList = CSV.readCSV(fileName);

        HashMap<Protein, ArrayList<String>> matches = new HashMap<Protein, ArrayList<String>>();

        matches = targetPeptide.findPotentialMatches(proteinList);

        CSV.writeCSV(matches);

         */
    }
}
