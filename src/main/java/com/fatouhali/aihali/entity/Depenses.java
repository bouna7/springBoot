package com.fatouhali.aihali.entity;

import java.util.List;
import java.util.Map;

public record Depenses(double salaire, List<Map> depenses, double totalDepense, double montant) {
}
