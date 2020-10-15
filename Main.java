import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;



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



    public void findPotentialMatches()
    {
        String protein = "YMKATWVDDAGHJIECQARNDW";

        String inPeptide = this.peptide;

        int peptideLength = peptide.length();
        int proteinLength = protein.length();

        String proteinSubstring = "";

        boolean match = false;
        for (int i = 0; i < protein.length() && match == false; i++)
        {
            if (protein.charAt(i) == inPeptide.charAt(0))
            {
                int endIndex = i + peptideLength;

                if (endIndex > protein.length())
                {
                    endIndex = proteinLength;
                }

                proteinSubstring = protein.substring(i, endIndex);

                System.out.println("Protein substring");
                System.out.println(proteinSubstring);

                match = isMatch(peptide, proteinSubstring);
            }
        }

        if (match)
        {
            System.out.print(String.format("Peptide %s was found within protein %s", peptide, protein));
        }
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



    @Override
    public String toString()
    {
        return protein;
    }
}






public class Main
{
    public static void printProteinList(ArrayList<Protein> inList)
    {
        for (Protein p : inList)
        {
            System.out.println(p);
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
        try
        {
            fileReader = new Scanner(new File(fileName));
        }

        catch (FileNotFoundException fileError)
        {
            System.out.println(String.format("There was a problem opening file \"%s\": error = %s", fileName, fileError.getMessage()));

            System.out.println("Exiting program...");

            System.exit(1);
        }



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

        printProteinList(proteinList);

        //targetPeptide.findPotentialMatches();
    }
}
