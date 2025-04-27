class WeatherInfo {
  final String time;
  final int rainProbability;
  final double rainAmount;
  final int cloudCoverage;

  WeatherInfo({
    required this.time,
    required this.rainProbability,
    required this.rainAmount,
    required this.cloudCoverage,
  });

  factory WeatherInfo.fromJson(Map<String, dynamic> json) {
    return WeatherInfo(
      time: json['time'] as String,
      rainProbability: json['precipitation_probability'] as int,
      rainAmount: (json['precipitation'] as num).toDouble(),
      cloudCoverage: json['cloud_cover'] as int,
    );
  }
}
