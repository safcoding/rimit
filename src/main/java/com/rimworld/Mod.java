package com.rimworld;

import java.util.List;

public record Mod(String name, String packageId, List<String> dependencies) {}