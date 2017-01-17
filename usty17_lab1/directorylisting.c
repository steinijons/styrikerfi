#include <stdio.h>
#include <errno.h>
#include <fcntl.h>
#include <dirent.h>

int main(int argc, char* argv[])
{
	DIR *dp;
	struct dirent *directoryEntry;
	if(argc == 1){
		puts("argument missing");
		dp = opendir(".");
		if(dp != NULL){
			while(directoryEntry = readdir(dp));
				printf("%s\n",directoryEntry->d_name);
		closedir(dp);
		}
	}
	else{
		int fd = open(argv[1], O_RDONLY);
		if(fd == -1){
			int errnr = errno;
			printf("errno: %4d \n", errnr);
			if(errnr == 20){
				puts("not a directory");
				fd = open("directorylisting.c", O_RDONLY); 
			}
			else if(errnr == 2){
				puts("No such file or directory");
			}
		}
		else{//File open
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
			
			DIR *dp;
			struct dirent *directoryEntry;
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
