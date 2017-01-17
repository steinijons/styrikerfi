#include <stdio.h>
#include <errno.h>
#include <fcntl.h>
#include <dirent.h>

int main(int argc, char* argv[])
{
	//Ef enginn parameter kemur inn á að skrifast út núverandi mappa VIRKAR EKKI!
	DIR *dp;
        struct dirent *directoryEntry;
	if(argc == 1){
		puts("argument missing");
		dp = opendir(".");
		if(dp != NULL){
			while((directoryEntry = readdir(dp)) != NULL);
				printf("%s\n", directoryEntry->d_name);
		}
		closedir(dp);	
	}
	else{	//Error handling
		int fd = open(argv[1], O_RDONLY);
		if(fd == -1){
			int errnr = errno;
			printf("errno: %4d \n", errnr);
			if(errnr == 2){
				puts("No such file or directory");
			}
		}
		else{
			//File open - Virkar ekki að skrifa út fyrstu 1024 byteinn.
			int  c;
			int counter = 0;
			FILE *file;
			puts("file open");
			file = fopen(argv[1], "r");
			if(file){
				while((c = getc(file)) != EOF && counter <= 1024){
					putchar(c);
					counter++;
				}
				fclose(file);
			}
			//Directory open
			dp = opendir(argv[1]);
			if(dp != NULL){
				while(directoryEntry = readdir(dp))
					printf("%s\n", directoryEntry->d_name);
			closedir(dp);
			} 
		} 
	return 0;
	}
}
