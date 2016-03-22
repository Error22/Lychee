#Lychee
Lychee is an open source deobfuscator with real time collaboration support allowing multiple people to work together in real time to deobfuscate a program, no need to manage commits and resolve conflicts. The server works to keep everyone up to date and to output the final mappings that can be used to deobfuscate other copies of the code. 

##Reason for creation
Lychee was created as large scale deobfuscation projects are hard to manage and often rely on a single person, which can take weeks, especially if that person is busy or even worse that person takes a break! You normally are limited to one or two people working on deobfuscation at a time as you have to separate out classes and some changes affect other changes, such as what a person names an interface and the knock effects on naming conventions. There are also no nice and simple point and click deobfuscators around, or none that I know of. Why should you have to edit text files manually and have to wait ages to see the impact of the changes?

From these issues the idea for Lychee was born. A simple deobfuscator with support for collaboration, easy remapping, near-instant previewing of changes and automatic mapping generation. 

##How it works
Lychee is separated into 3 key parts:
- The Server – This handles keeping all of the editor clients synced up and the final mapping generation.
- The Editor – The place where you apply mapping changes and collaborate
- The Client – The minimal program you can redistribute to apply mappings, especially useful when creating modding toolkits!

##Main Features:
-	Coming Soon!

##Planned Features (In rough order)
- Simple point and click mapping generatin
- Automatic method pairing - some interfaces share the same method names
- Automatic generation
- Auto helpers - Auto rename method args to arg1, arg2 etc
- Remove string obfuscation
-	Accounts
-	real time collaboration
-	Instant preview of changes
-	Permissions
-	Blame & Rollback -  See who broke something
-	Todo – Mark things that need to be done
-	Multiple projects
-	Automatic final mapping downloading to client
-	Web editor – Maybe completely move away from editor program?
-	Code patching - Add/remove/change classes/methods/fields internals
