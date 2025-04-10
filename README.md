Banking task

How to customise account types, withdrawal limits, and overdraft limits:
Open account_types.txt
For every account type you want, write a line like so:

name,withdrawal_limit,overdraft_limit

So if you want a savings type account with 1000 overdraft and a 5000 withdrawal limit:

savings,-5000,-1000

DO NOT use capital letters
DO NOT make duplicates
DO use negative numbers for withdrawal limits
DO use negative numbers for overdraft limits

New account types will require a program restart to take effect

IMPORTANT -
Feel free to alter transaction limits at will and add new types but if you REMOVE types you must manualy delete each account of that type from bankData.csv - the program cannot load an account with a type of null
