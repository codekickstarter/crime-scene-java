# Your code as a crime scene - Code maat

A java implementation of the hotspot analysis done in the book "Your code as a crime scene".

I really like the simplicity of this analysis. The book uses the author's tool "Code Maat", python and an external tool (cloc) to count the lines of code. I decided to make a simple java program that combines all these steps.

## Preparing the input

Execute this git command to get a list of all the changes that were made to files as a csv file. This is the required input for the program.

    git log --all --numstat --date=short --pretty=format:'--%h--%ad--%aN' --no-renames --no-merges > cm_input.csv

The application will output a CSV file that shows the amount of changes made to every file and the complexity of the file.
