#include <stdio.h>
#include <errno.h>
#include <unistd.h>

int main(int argc, char* argv[])
{
	int fd[2];
	int err = pipe(fd);
	
	if(err == -1) {
		int error = errno;
		printf("error: %d", error);
	}	

        execl("./dirlst", "./dirlist", (char *) NULL);

	puts("hello world");
	int status = 0;
	
	pid_t pid1 = fork();
	pid_t pid2 = fork();
	pid_t pid3 = fork();
	pid_t pid4 = fork();	

	puts("process starting");
	
	if(pid1 != 0) {
		waitpid(pid1, &status, 0);
	}

	if(pid2 != 0) {
		waitpid(pid2, &status, 0);
	}
	
	if(pid3 != 0) {
		waitpid(pid3, &status, 0);
	}

	if(pid4 != 0) {
		waitpid(pid4, &status, 0);
	}

	int i = 0;
	for(; i < 100000000; i++){
	}	
	
	puts("process finish");


	execl("./dirlstsample", "./dirlstsample", (char *) NULL);	
}

