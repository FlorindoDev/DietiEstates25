import 'package:dietiestate25/main.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'package:dietiestate25/Model/Tempo/WeatherInfo.dart';
import 'package:dietiestate25/Booking/BookingController.dart';
import 'package:dietiestate25/Model/Estate/Estate.dart';

class BookingWindow extends StatefulWidget {
  final Estate estate;
  const BookingWindow({Key? key, required this.estate}) : super(key: key);

  @override
  _BookingWindowState createState() => _BookingWindowState();
}

class _BookingWindowState extends State<BookingWindow> {
  DateTime _displayedMonth = DateTime.now();
  DateTime? _selectedDate;
  Future<List<WeatherInfo>>? _weatherFuture;
  bool _localeReady = false;

  @override
  void initState() {
    super.initState();
    _initLocale();
  }

  Future<void> _initLocale() async {
    await initializeDateFormatting('it_IT', null);
    setState(() {
      _localeReady = true;
    });
  }

  @override
  Widget build(BuildContext context) {
    if (!_localeReady) {
      return Center(
        child: CircularProgressIndicator(
          strokeWidth: 5,
        ),
      );
    }

    //dart quando mettaimo giorno 0 intediamo l'ultimo giorno del mese N-1
    // quindi facciamo month+1
    final daysInMonth =
        DateTime(_displayedMonth.year, _displayedMonth.month + 1, 0).day;

    //Trovo il giorno della settimana del primo giorno del mese
    final firstWeekday =
        DateTime(_displayedMonth.year, _displayedMonth.month, 1).weekday;

    //Calcolo quanti spazi vuoti (celle vuote) servono prima del giorno 1
    //quindi se firstWeekday = 3 mercoledi allora faro firstWeekday-1 cosi lunedi e martedi sarno vuoti
    final offset = firstWeekday - 1; // Monday=1

    //Numero iniziale di celle: celle vuote + giorni effettivi del mese
    int totalCells = offset + daysInMonth;

    //se il numero di celle è divisibile per 7 tutto ok
    //se non è divisibile per 7 allora mi calcolo quando spazio manca quindi il resto(totalCells % 7) meno 7 per avere un numero che sommato a il numero di celle sia divisibile per 7
    final padEnd = totalCells % 7 == 0 ? 0 : 7 - totalCells % 7;
    totalCells += padEnd;

    final monthName =
        DateFormat.MMMM('it_IT').format(_displayedMonth).toUpperCase();

    return Scaffold(
      backgroundColor: MyApp.panna,
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.only(bottom: 16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Padding(
                padding:
                    const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                child: Row(
                  children: [
                    GestureDetector(
                      onTap: () => Navigator.of(context).pop(),
                      child: Row(
                        children: const [
                          Icon(Icons.arrow_back),
                          SizedBox(width: 8),
                          Text('Indietro',
                              style: TextStyle(
                                  fontSize: 16, fontWeight: FontWeight.w500)),
                        ],
                      ),
                    ),
                    const Spacer(),
                    const Icon(Icons.home, color: MyApp.celeste),
                    const SizedBox(width: 4),
                    RichText(
                      text: const TextSpan(
                        text: 'DietiEstates',
                        style: TextStyle(
                            color: Colors.black,
                            fontSize: 18,
                            fontWeight: FontWeight.bold),
                        children: [
                          TextSpan(
                              text: '25', style: TextStyle(color: MyApp.rosso))
                        ],
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 16),
              Padding(
                padding: EdgeInsets.symmetric(horizontal: 16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text('Stai prenotando per immobile',
                        style: TextStyle(
                            fontSize: 20, fontWeight: FontWeight.bold)),
                    SizedBox(height: 4),
                    Text(
                        '${widget.estate.indirizzo?.via}, ${widget.estate.indirizzo?.numeroCivico}, ${widget.estate.indirizzo?.citta}, ${widget.estate.indirizzo?.cap}',
                        style: TextStyle(fontSize: 16, color: Colors.black54)),
                  ],
                ),
              ),
              const SizedBox(height: 16),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16),
                child: Card(
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(16)),
                  child: Padding(
                    padding: const EdgeInsets.all(12),
                    child: Column(
                      children: [
                        // Month and Year Selector
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            IconButton(
                              icon: const Icon(Icons.chevron_left),
                              onPressed: () {
                                setState(() {
                                  _displayedMonth = DateTime(
                                      _displayedMonth.year,
                                      _displayedMonth.month - 1,
                                      1);
                                });
                              },
                            ),
                            Text(
                              '$monthName ${_displayedMonth.year}',
                              style: const TextStyle(
                                  fontSize: 16, fontWeight: FontWeight.bold),
                            ),
                            IconButton(
                              icon: const Icon(Icons.chevron_right),
                              onPressed: () {
                                setState(() {
                                  _displayedMonth = DateTime(
                                      _displayedMonth.year,
                                      _displayedMonth.month + 1,
                                      1);
                                });
                              },
                            ),
                          ],
                        ),
                        const SizedBox(height: 12),
                        // Weekday Labels centered in each cell
                        Row(
                          children:
                              ['Lun', 'Mar', 'Mer', 'Gio', 'Ven', 'Sab', 'Dom']
                                  .map((d) => Expanded(
                                        child: Center(
                                          child: Text(
                                            d,
                                            style: const TextStyle(
                                                fontSize: 12,
                                                fontWeight: FontWeight.w500,
                                                color: Colors.black54),
                                          ),
                                        ),
                                      ))
                                  .toList(),
                        ),
                        const SizedBox(height: 12),
                        // Days Grid
                        GridView.builder(
                          shrinkWrap: true,
                          physics: const NeverScrollableScrollPhysics(),
                          itemCount: totalCells,
                          gridDelegate:
                              const SliverGridDelegateWithFixedCrossAxisCount(
                            crossAxisCount: 7,
                            mainAxisSpacing: 8,
                            crossAxisSpacing: 8,
                            childAspectRatio: 1,
                          ),
                          itemBuilder: (context, index) {
                            if (index < offset ||
                                index >= offset + daysInMonth) {
                              return const SizedBox();
                            }
                            final day = index - offset + 1;
                            final date = DateTime(_displayedMonth.year,
                                _displayedMonth.month, day);
                            final isSelected =
                                _selectedDate != null && _selectedDate == date;
                            return GestureDetector(
                              onTap: () {
                                setState(() {
                                  _selectedDate = date;
                                  //TODO le cordinate devono essere quelle del immobile
                                  _weatherFuture =
                                      BookingController.fetchWeatherList(
                                    context,
                                    date,
                                    "40.8762",
                                    "14.5195",
                                  );
                                });
                              },
                              child: Container(
                                decoration: BoxDecoration(
                                  shape: BoxShape.circle,
                                  color:
                                      isSelected ? MyApp.rosso : MyApp.celeste,
                                ),
                                alignment: Alignment.center,
                                child: Text(
                                  '$day',
                                  style: const TextStyle(
                                      color: Colors.white,
                                      fontWeight: FontWeight.w600),
                                ),
                              ),
                            );
                          },
                        ),
                      ],
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 16),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16),
                child: Card(
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(16)),
                  child: Padding(
                    padding: const EdgeInsets.all(16),
                    child: _selectedDate == null
                        ? Center(
                            child: Text(
                              'Seleziona una data',
                              style: const TextStyle(
                                  fontSize: 16, fontWeight: FontWeight.bold),
                            ),
                          )
                        : Column(
                            children: [
                              Center(
                                child: Text(
                                  'Tempo del giorno ${DateFormat('d MMMM', 'it_IT').format(_selectedDate!)}',
                                  style: TextStyle(
                                      fontSize: 16,
                                      fontWeight: FontWeight.bold),
                                ),
                              ),
                              const SizedBox(height: 12),
                              FutureBuilder<List<WeatherInfo>>(
                                future: _weatherFuture,
                                builder: (context, snapshot) {
                                  if (snapshot.connectionState ==
                                      ConnectionState.waiting) {
                                    return Center(
                                        child: CircularProgressIndicator());
                                  } else if (snapshot.hasError) {
                                    return Text('Errore: ${snapshot.error}');
                                  } else if (!snapshot.hasData ||
                                      snapshot.data!.isEmpty) {
                                    return Text(
                                        'Nessuna info meteo disponibile');
                                  }

                                  final list = snapshot.data!;
                                  return SingleChildScrollView(
                                    scrollDirection: Axis.horizontal,
                                    child: SizedBox(
                                      width: 480, // regola se serve più spazio
                                      child: Column(
                                        crossAxisAlignment:
                                            CrossAxisAlignment.stretch,
                                        children: [
                                          const SizedBox(height: 8),
                                          // Riga di header con i titoli di colonna
                                          Padding(
                                            padding: const EdgeInsets.symmetric(
                                                horizontal: 4),
                                            child: Row(
                                              children: [
                                                // spazio per icona + orario
                                                const SizedBox(
                                                    width: 28 + 4 + 50),
                                                Expanded(
                                                  child: Text(
                                                    'Prob. pioggia',
                                                    textAlign: TextAlign.center,
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        color: Colors.black54),
                                                  ),
                                                ),
                                                const SizedBox(width: 8),
                                                Expanded(
                                                  child: Text(
                                                    'Pioggia caduta',
                                                    textAlign: TextAlign.center,
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        color: Colors.black54),
                                                  ),
                                                ),
                                                const SizedBox(width: 8),
                                                Expanded(
                                                  child: Text(
                                                    'Nuvoloso',
                                                    textAlign: TextAlign.center,
                                                    style: TextStyle(
                                                        fontSize: 12,
                                                        color: Colors.black54),
                                                  ),
                                                ),
                                              ],
                                            ),
                                          ),
                                          const SizedBox(height: 4),
                                          // Lista oraria
                                          SizedBox(
                                            height: 300,
                                            child: ListView.separated(
                                              itemCount: list.length,
                                              separatorBuilder: (_, __) =>
                                                  const Divider(height: 1),
                                              itemBuilder: (context, i) {
                                                final w = list[i];
                                                return Padding(
                                                  padding: const EdgeInsets
                                                      .symmetric(
                                                      vertical: 8,
                                                      horizontal: 4),
                                                  child: Row(
                                                    children: [
                                                      Icon(Icons.wb_sunny,
                                                          size: 24,
                                                          color: MyApp.celeste),
                                                      const SizedBox(width: 4),
                                                      SizedBox(
                                                        width: 50,
                                                        child: Text(
                                                          w.time,
                                                          style: TextStyle(
                                                              fontSize: 16,
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w600),
                                                        ),
                                                      ),
                                                      const SizedBox(width: 8),
                                                      Expanded(
                                                        child: Text(
                                                          '${w.rainProbability}%',
                                                          textAlign:
                                                              TextAlign.center,
                                                          style: TextStyle(
                                                              fontWeight:
                                                                  FontWeight
                                                                      .bold),
                                                        ),
                                                      ),
                                                      const SizedBox(width: 8),
                                                      Expanded(
                                                        child: Text(
                                                          '${w.rainAmount}mm',
                                                          textAlign:
                                                              TextAlign.center,
                                                          style: TextStyle(
                                                              fontWeight:
                                                                  FontWeight
                                                                      .bold),
                                                        ),
                                                      ),
                                                      const SizedBox(width: 8),
                                                      Expanded(
                                                        child: Text(
                                                          '${w.cloudCoverage}%',
                                                          textAlign:
                                                              TextAlign.center,
                                                          style: TextStyle(
                                                              fontWeight:
                                                                  FontWeight
                                                                      .bold),
                                                        ),
                                                      ),
                                                    ],
                                                  ),
                                                );
                                              },
                                            ),
                                          ),
                                        ],
                                      ),
                                    ),
                                  );
                                },
                              ),
                            ],
                          ),
                  ),
                ),
              ),
              const SizedBox(height: 16),
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16),
                child: SizedBox(
                  width: double.infinity,
                  height: 50,
                  child: ElevatedButton(
                    onPressed: _selectedDate != null
                        ? () {
                            BookingController.makeAppointment(
                                context, widget.estate, _selectedDate);
                          }
                        : null,
                    style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.red,
                        shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(25))),
                    child: const Text('Conferma',
                        style: TextStyle(fontSize: 18, color: Colors.white)),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
