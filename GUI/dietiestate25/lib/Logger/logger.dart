import 'package:logger/logger.dart';

class MyLogger {
  static final Logger _logger = Logger(
    printer: PrettyPrinter(),
  );

  static Logger getIstance() {
    return _logger;
  }

}
