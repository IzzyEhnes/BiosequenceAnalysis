import java.util.Scanner;



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
}






public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter a peptide: ");
        Peptide targetPeptide = new Peptide(scanner.nextLine());

        System.out.println(targetPeptide);

        targetPeptide.findPotentialMatches();
    }
}
