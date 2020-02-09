with open("input.txt", "r") as file:
	genes = file.readlines()

normGenes = []
mutGenes = []

for line in genes:
	if line != "\n":
		gen = line.replace("\n", "")
		gen = gen.split(",")
		if gen[0] == "NORM":
			for i in range(1, len(gen)):
				if int(gen[i]) not in normGenes:
					normGenes.append(int(gen[i]))
		else:
			for i in range(1, len(gen)):
				if int(gen[i]) not in mutGenes:
					mutGenes.append(int(gen[i]))
	else:
		if len(normGenes) != 0:
			if len(mutGenes) != 0:
				mutGenesNew = list(set(mutGenes) - set(normGenes))
				if len(mutGenesNew) != 0:
					normGenesCount = len(normGenes)
					mutGenesCount = len(mutGenesNew)

					print("MUT COUNT:", str(mutGenesCount))
					print("NORM COUNT:", str(normGenesCount))

					fullGenes = list(set(normGenes) | set(mutGenesNew))
					fullGenes.sort()
					for num in fullGenes:
						if num in mutGenesNew:
							print(str(num) + ",MUT")
						else:
							print(str(num) + ",NORM")
				else:
					print("INCONSISTENT")
			else:
				print("MUT COUNT: 0")
				print("NORM COUNT:", str(len(normGenes)))
				normGenes.sort()
				for num in normGenes:
					print(str(num) + ",NORM")
		else:
			print("NONUNIQUE")

		print()
		normGenes = []
		mutGenes = []

