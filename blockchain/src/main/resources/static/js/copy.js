var clipboard = new ClipboardJS('.btn');
clipboard.on('success', function(e) {
    alert("Copied!");
});
clipboard.on('error', function(e) {
    console.error('Trigger:', e.trigger);
});