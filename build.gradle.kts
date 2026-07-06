import java.net.URLEncoder
import java.nio.charset.StandardCharsets

plugins {
	base
	alias(libs.plugins.minotaur)
}

val minecraftCompatible: String by project

val modrinthId: String by project
val git: String by project
val gitReference: String? = System.getenv("GITHUB_REF")

fun changelog(): String {
	val changelog = System.getenv("CHANGELOG")
	if (changelog != null) {
		return changelog
	}

	if (gitReference != null && gitReference.startsWith("refs/tags/")) {
		return "You may view the changelog at $git/releases/tag/${URLEncoder.encode(gitReference.substring(10), StandardCharsets.UTF_8.name())}"
	}

	return "No changelog is available. Perhaps poke at $git for a changelog?"
}

tasks {
	register<Zip>("zip") {
		// Metadata
		archiveBaseName = rootProject.name
		archiveVersion = version.toString()

		group = "build"

		// Reproducibility
		isPreserveFileTimestamps = false
		isReproducibleFileOrder = true

		fun folderCopy(vararg args: String) {
			for (arg in args) {
				from(arg) {
					into(arg)
				}
			}
		}

		from(rootProject.projectDir) {
			include("LICENSE*")
			// Do you ever have some regex stew? It's pretty tasty.
			rename("LICENSE(\\-.*+$)?", "LICENSE-${rootProject.name.uppercase()}$1")
		}

		folderCopy(
			"assets",
			"modern_title"
		)

		from(
			"pack.mcmeta",
			"pack.png",
		)
	}

	assemble {
		dependsOn("zip")
	}

	"modrinth" {
		dependsOn("zip")
	}
}

modrinth {
	token.set(System.getenv("MODRINTH_TOKEN"))
	projectId.set(modrinthId)
	changelog.set(changelog())
	gameVersions.set(minecraftCompatible.split("\\s*,\\s*"))

	file.set(tasks.named<Zip>("zip").get().archiveFile)

	// Who said minotaur was for mods?
	// We're in Gradle land :3
	loaders.set(setOf("minecraft"))
}