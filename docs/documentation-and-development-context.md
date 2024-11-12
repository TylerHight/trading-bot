# Trading Bot Project: Development Context and Documentation
[Last Updated: 2024-11-11]

## ASSISTANT CONTEXT SECTION
This section helps maintain conversation-to-conversation continuity. When sharing this file, add your current focus below:

Current Development Focus:
- Active Task: [e.g., "Implementing chart theme improvements"]
- Last Change: [e.g., "Fixed time series axis label spacing"]
- Next Task: [e.g., "Adding tooltip customization"]

Recent Code Changes (Last 3 conversations):
1. [Description and date of most recent change]
2. [Second most recent]
3. [Third most recent]

Known Context Gaps:
- [List any information that was unclear or missing from previous conversations]
- [Note any assumptions made]

## IMPLEMENTATION STATUS

### Currently Working Features ✓
- Theme system (light/dark/system)
- Base chart components
- Time series visualization
- Theme-aware tooltips

### In Progress 🔄
- WebSocket real-time updates
- Interactive chart features
- Error boundary implementation

### Planned Features 📋
- Advanced technical indicators
- Strategy implementation
- Order execution system

## TECHNICAL SPECIFICATIONS

### Core Data Types
```typescript
// Currently in use
interface TimeSeriesPoint {
  timestamp: string;
  value: number;
}

interface FrequencyPoint {
  frequency: string;
  magnitude: number;
}

interface ThemeContextType {
  theme: Theme;
  setTheme: (theme: Theme) => void;
}

type Theme = 'light' | 'dark' | 'system';

// Planned/In Progress
interface TechnicalIndicators {
  movingAverages: {
    sma: number[];
    ema: number[];
  };
  momentum: {
    rsi: number[];
    macd: {
      line: number[];
      signal: number[];
      histogram: number[];
    };
  };
}
```

### Current Component Implementation
```typescript
// Currently using this TimeSeriesChart implementation
const TimeSeriesChart = ({ data }: { data: TimeSeriesPoint[] }) => {
  const { theme } = useTheme();
  
  const themeColors = {
    text: theme === 'dark' ? 'rgba(255, 255, 255, 0.87)' : 'rgba(0, 0, 0, 0.87)',
    grid: theme === 'dark' ? 'rgba(255, 255, 255, 0.1)' : 'rgba(0, 0, 0, 0.1)',
    tooltip: {
      background: theme === 'dark' ? '#1a1a1a' : '#ffffff',
      border: theme === 'dark' ? 'rgba(255, 255, 255, 0.1)' : 'rgba(0, 0, 0, 0.1)',
    }
  };

  return (
    <ResponsiveContainer width="100%" height={400}>
      <LineChart
        data={data}
        margin={{ top: 20, right: 30, left: 50, bottom: 85 }}
      >
        <CartesianGrid strokeDasharray="3 3" stroke={themeColors.grid} />
        <XAxis 
          dataKey="timestamp" 
          height={60}
          angle={-45}
          textAnchor="end"
          stroke={themeColors.text}
          tick={{ fill: themeColors.text }}
          label={{ 
            value: 'Time', 
            position: 'insideBottom', 
            offset: -15,
            fill: themeColors.text,
            dy: 10
          }}
        />
        <YAxis 
          stroke={themeColors.text}
          tick={{ fill: themeColors.text }}
          label={{ 
            value: 'Value', 
            angle: -90, 
            position: 'insideLeft',
            offset: -5,
            fill: themeColors.text
          }}
        />
        <Tooltip 
          contentStyle={{ 
            backgroundColor: themeColors.tooltip.background,
            border: `1px solid ${themeColors.tooltip.border}`,
            color: themeColors.text
          }}
          labelStyle={{ color: themeColors.text }}
          itemStyle={{ color: themeColors.text }}
        />
        <Legend 
          verticalAlign="top"
          height={36}
          wrapperStyle={{ 
            color: themeColors.text,
            paddingTop: '10px'
          }}
        />
        <Line 
          type="monotone" 
          dataKey="value" 
          stroke="#8884d8" 
          name="Signal"
          dot={false}
        />
      </LineChart>
    </ResponsiveContainer>
  );
};
```

## DEVELOPMENT NOTES

### Project Structure
```
monitoring-frontend/
├── src/
│   ├── components/
│   │   ├── charts/
│   │   │   ├── TimeSeriesChart.tsx    # ✓ Implemented
│   │   │   └── FourierChart.tsx       # ✓ Implemented
│   │   └── theme/
│   │       ├── theme-provider.tsx     # ✓ Implemented
│   │       └── theme-toggle.tsx       # ✓ Implemented
```

### Current Dependencies
```json
{
  "dependencies": {
    "react": "18.2.0",
    "recharts": "2.10.3",
    "lucide-react": "0.263.1"
  }
}
```

### Known Issues
1. ✓ Fixed: Chart text colors in system theme
2. ✓ Fixed: Time series X-axis label spacing
3. 🔄 In Progress: WebSocket connection stability
4. 📋 Planned: Chart performance optimization

### Style Guidelines
- Using Tailwind CSS for styling
- Following shadcn/ui component patterns
- Theme-aware color system implementation

## CONVERSATION HISTORY NOTES
[Add brief notes about major decisions/changes from previous conversations here]

Last Conversation Summary:
- Date: [Last conversation date]
- Major Decisions: [List major decisions made]
- Changes Implemented: [List changes implemented]
- Questions Resolved: [List questions that were answered]

## INSTRUCTIONS FOR NEXT CONVERSATION
When starting a new conversation with this project:

1. Copy this entire file
2. Update the "Current Development Focus" section
3. Add any new changes to the appropriate sections
4. Share the updated file

The assistant will:
1. Maintain context from previous conversations
2. Reference implemented features correctly
3. Suggest improvements based on current status
4. Help maintain code consistency

## VERSION CONTROL
Document Version: 1.0
Last Editor: Assistant
Last Edit Date: 2024-11-11

[End of Document]