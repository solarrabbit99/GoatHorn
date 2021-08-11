# GoatHorn

[![](https://jitpack.io/v/zhenghanlee/GoatHorn-API.svg)](https://jitpack.io/#zhenghanlee/GoatHorn-API)
[![License](https://img.shields.io/github/license/zhenghanlee/GoatHorn)](https://img.shields.io/github/license/zhenghanlee/CustomShop)
[![Spigot Downloads](http://badge.henrya.org/spigotbukkit/downloads?spigot=95113&name=spigot_downloads)](https://www.spigotmc.org/resources/%E2%AD%901-17-must-have%E2%AD%90-goathorn.95113/)
[![Commit Activity](https://img.shields.io/github/commit-activity/m/zhenghanlee/GoatHorn)](https://img.shields.io/github/commit-activity/m/zhenghanlee/GoatHorn)
[![Discord](https://img.shields.io/discord/846941711741222922.svg?logo=discord)](https://discord.gg/YSv7pptDjE)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/9e997f06079542b9996cf7c695989b9d)](https://www.codacy.com/gh/zhenghanlee/GoatHorn/dashboard?utm_source=github.com&utm_medium=referral&utm_content=zhenghanlee/GoatHorn&utm_campaign=Badge_Grade)

## Introduction

The goal of this project is two-fold:

1. Provide a plugin that implements goat horns, which are experimental features in Bedrock edition, planned to be released on 1.18. Java edition shall have equivalent experience with the item, and we are lucky for it to be possible to be implemented in 1.17.x. The only difference is that horns are looted via killing the goat instead of making goats ramp blocks - a behaviour that has yet to be present in Java 1.17.x.

2. Provide an API for developers to decide on whether blaring a goat horn trigger any in-game events (such as raids, buff effects, summoning of mobs etc). This is possible via the introduction of `PlayerBlareHornEvent`.

## Maven Repository

You can add the project as your dependency by including the JitPack repository in your `pom.xml`:

```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```

Then after add the dependency like so (replace `VERSION` with the version provided by the jitpack badge located at the start of this document):

```xml
<dependency>
	<groupId>com.github.zhenghanlee</groupId>
	<artifactId>GoatHorn-API</artifactId>
	<version>VERSION</version>
</dependency>
```

## Gradle Repository

You can add the project as your dependency by including the JitPack repository:

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Then after add the dependency like so (replace `VERSION` with the version provided by the jitpack badge located at the start of this document):

```gradle
dependencies {
	    implementation 'com.github.zhenghanlee:GoatHorn-API:VERSION'
}
```

## Example

To disable player from blaring horns:

```java
@EventHandler
public void onBlare(PlayerBlareHornEvent event) {
    if (event.getPlayer().hasPermission("server.rank.example"))
        event.setCancelled(true);
}
```

Calling a raid if player is in the vacanity of a village:

```java
@EventHandler
public void onBlareHorn(PlayerBlareHornEvent evt) {
    evt.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 1, 5));
}

```
