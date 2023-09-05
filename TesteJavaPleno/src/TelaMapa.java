import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class TelaMapa {

    private ListaContatos listaContatos;

    public TelaMapa(ListaContatos listaContatos) {
        this.listaContatos = listaContatos;
    }

    public void createAndShowGUI() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ver Mapa");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 700);

            JPanel panel = new JPanel(new BorderLayout());

            // Crie um mapa do Google Maps
            JPanel mapaPanel = new JPanel(new BorderLayout());
            GoogleMapPanel googleMapPanel = new GoogleMapPanel();
            mapaPanel.add(googleMapPanel, BorderLayout.CENTER);

            // Adicione marcadores para cada contato na lista
            List<Contato> contatos = listaContatos.getContatos();
            for (Contato contato : contatos) {
                // Substitua o seguinte código com a obtenção do endereço correto do seu contato
                String endereco = contato.getEndereco();

                // Use o Google Geocoding para obter as coordenadas do endereço
                LatLng coordenadas = obterCoordenadasGoogleGeocoding(endereco);

                // Adicione um marcador ao mapa com as coordenadas
                googleMapPanel.addMarker(new Marker(coordenadas, contato.getNome()));
            }

            panel.add(mapaPanel, BorderLayout.CENTER);

            // Botão "Voltar"
            JButton voltarButton = new JButton("Voltar");
            voltarButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    new TelaPrincipal(listaContatos).createAndShowGUI();
                }
            });

            panel.add(voltarButton, BorderLayout.PAGE_END);

            frame.add(panel);
            frame.setVisible(true);
        });
    }

    private LatLng obterCoordenadasGoogleGeocoding(String endereco) {
        try {
            GeoApiContext context = new GeoApiContext.Builder().apiKey("SUA_API_KEY").build();
            GeocodingResult[] results = GeocodingApi.geocode(context, endereco).await();

            if (results.length > 0) {
                LatLng location = results[0].geometry.location;
                return new LatLng(location.lat, location.lng);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Se não for possível obter as coordenadas, retorne um valor padrão
        return new LatLng(0, 0);
    }
}
