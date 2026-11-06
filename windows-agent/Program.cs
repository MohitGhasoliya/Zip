using System.Drawing.Imaging;

namespace MobiPC.WindowsAgent;

internal static class Program
{
    [STAThread]
    static void Main()
    {
        ApplicationConfiguration.Initialize();
        Application.Run(new AgentForm());
    }
}

internal sealed class AgentForm : Form
{
    private readonly Label status = new() { Text = "Not sharing", AutoSize = true, ForeColor = Color.White };
    private readonly PictureBox preview = new() { Dock = DockStyle.Fill, SizeMode = PictureBoxSizeMode.Zoom, BackColor = Color.FromArgb(18, 24, 36) };
    private readonly System.Windows.Forms.Timer timer = new() { Interval = 700 };
    private bool sharing;

    public AgentForm()
    {
        Text = "MobiPC Connect — Windows Agent";
        MinimumSize = new Size(760, 520);
        BackColor = Color.FromArgb(12, 18, 30);
        ForeColor = Color.White;
        var header = new Panel { Dock = DockStyle.Top, Height = 100, Padding = new Padding(20) };
        var title = new Label { Text = "MobiPC Connect", Font = new Font("Segoe UI", 20, FontStyle.Bold), AutoSize = true, ForeColor = Color.White, Location = new Point(20, 12) };
        status.Location = new Point(22, 58);
        var start = new Button { Text = "Start local preview", Width = 170, Height = 38, Location = new Point(490, 24) };
        var stop = new Button { Text = "Stop sharing", Width = 130, Height = 38, Location = new Point(665, 24), Enabled = false };
        start.Click += (_, _) => { sharing = true; status.Text = "● Sharing preview locally — this is not being sent over the network"; start.Enabled = false; stop.Enabled = true; timer.Start(); Capture(); };
        stop.Click += (_, _) => { sharing = false; timer.Stop(); preview.Image?.Dispose(); preview.Image = null; status.Text = "Not sharing"; start.Enabled = true; stop.Enabled = false; };
        header.Controls.AddRange([title, status, start, stop]);
        Controls.Add(preview); Controls.Add(header);
        timer.Tick += (_, _) => { if (sharing) Capture(); };
        FormClosing += (_, _) => { timer.Stop(); preview.Image?.Dispose(); };
    }

    private void Capture()
    {
        try
        {
            var bounds = Screen.PrimaryScreen?.Bounds ?? Bounds;
            using var bitmap = new Bitmap(Math.Max(1, bounds.Width / 2), Math.Max(1, bounds.Height / 2));
            using (var g = Graphics.FromImage(bitmap)) g.CopyFromScreen(bounds.Left, bounds.Top, 0, 0, bitmap.Size);
            var old = preview.Image;
            preview.Image = (Bitmap)bitmap.Clone();
            old?.Dispose();
        }
        catch (Exception ex) { status.Text = "Preview error: " + ex.Message; }
    }
}
