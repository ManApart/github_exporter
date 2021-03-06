import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubRepo(val id: String, val name: String, val description: String? = "", val owner: GithubUser)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubUser(val id: String, val login: String)

data class EpicIssues(val epic_issues: List<Epic>)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Epic(var issue_number: String, var repo_id: String, val issue_url: String?, val issues: List<ZenIssue> = listOf()) {
    var owner = ""
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class ZenIssue(val issue_number: String, val repo_id: String, val estimate: Estimate?, val is_epic: Boolean, val pipeline: Pipeline?) {
    var epic: Epic? = null
}

data class Estimate(val value: Int)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Pipeline(val pipeline_id: String = "", val name: String = "")

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubIssue(val id: String, val number: String, val title: String, val labels: List<Label>, val body: String, val milestone: Milestone?, val updated_at: String = "", val pull_request: PullRequest? = null, val assignees: List<GithubUser> = listOf(), val state: String = "") {
    var repoName = ""
    var repoId = "0"
    var owner = ""
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Milestone(val id: String, val title: String = "")

@JsonIgnoreProperties(ignoreUnknown = true)
data class Label(val id: String, val name: String, val color: String)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PullRequest(val url: String)

class Links(header: String?) {
    val links = parseLinks(header)

    private fun parseLinks(header: String?): List<Link> {
        return header?.split(",")?.map { line ->
            val parts = line.trim().split(";").map { it.trim() }
            val url = parts[0].substring(1, parts[0].length - 1)
            val rel = parts[1].substring(5, parts[1].length - 1)
            Link(url, rel)
        } ?: listOf()
    }
}

class Link(val url: String, val rel: String)
