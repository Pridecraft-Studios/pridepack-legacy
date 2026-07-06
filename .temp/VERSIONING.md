# Pridepack Versioning

The versioning shall consist of three elements:

- Major Version `M.u+g`
- Fix Version `m.F+g`
- Minecraft (*G*ame) Version `m.f+G`

`Major` gets bumped with any big release, or any releases that come out considerably later than the previous  
`Fix` is any patch that is made to fix issues and doesn't justify a full Major bump
`Game` is the Minecraft version for that specific file.

<!--
With `Format` being the Minecraft versions' Resource/Texture Pack Format version, and the other ones following [`semver`](https://semver.org)

For example, in b1.7.3, the release is 1.0.0, because the `Resource Pack Format` didn't exist until 1.6. In the 1.6.1 version should be 1.1.0, as the `Resource Pack Format` version for 1.6.1 is `1`.
-->