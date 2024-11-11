export const getChartTheme = (isDarkMode: boolean) => {
    return {
      gridColor: isDarkMode ? 'rgba(255, 255, 255, 0.1)' : 'rgba(0, 0, 0, 0.1)',
      textColor: isDarkMode ? 'rgba(255, 255, 255, 0.87)' : 'rgba(0, 0, 0, 0.87)',
      lineColor: '#8884d8', // You can adjust this based on your theme
    };
  };
  
  // Helper function to adjust chart colors based on theme
  export const applyChartTheme = (theme: ReturnType<typeof getChartTheme>) => {
    return {
      cartesianGrid: {
        strokeDasharray: '3 3',
        stroke: theme.gridColor,
      },
      xAxis: {
        tick: {
          fill: theme.textColor,
        },
      },
      yAxis: {
        tick: {
          fill: theme.textColor,
        },
      },
      tooltip: {
        contentStyle: {
          backgroundColor: theme.textColor === '#ffffff' ? '#1a1a1a' : '#ffffff',
          color: theme.textColor === '#ffffff' ? '#ffffff' : '#000000',
        },
      },
    };
  };