import styled from "styled-components";

type ButtonBuzzerProps = {
    handleClick: () => void,
    buttonText?: string,
    color?: string
    $position?: string;
}

export default function ButtonBuzzer(props: Readonly<ButtonBuzzerProps>) {
    return (
        <ButtonContainer $position={props.$position}>
            <BuzzerButton type={"button"} onClick={props.handleClick} color={props.color}>
                <StyledSpan color={props.color}>{props.buttonText}</StyledSpan>
            </BuzzerButton>
        </ButtonContainer>
    )
}

const ButtonContainer = styled.div<{ $position?: string }>`
  display: flex;
  align-items: center;
  padding: 40px 30px;
  position: fixed;
  bottom: 0;
  width: ${({$position}) => ($position === "left" || $position === "right" ? "50%" : "100%")};
  ${({$position}) => `${$position}: 0`};
  justify-content: ${({$position}) => $position === "left" ? "flex-end" : $position === "right" ? "flex-start" : "center"};
`

const BuzzerButton = styled.button<{ color?: string }>`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100px;
  width: 100px;
  overflow: hidden;
  border-radius: 50%;
  border: 1px solid ${({color}) => color === "red" ? "#AD007E" : "#29A05C"};
  background: ${({color}) => color === "red" ? "linear-gradient(0deg, #850061, #EA33B4)" : "linear-gradient(0deg, #1E7644, #63C177)"};
  box-shadow: 0 20px 30px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.2s;

  &:hover {
    box-shadow: 0 2px 5px rgb(0, 0, 0, 0.3);
  }
`

const StyledSpan = styled.span<{ color?: string }>`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 80px;
  width: 80px;
  border: 1px solid ${({color}) => color === "red" ? "#AD007E" : "#29A05C"};
  border-radius: 50%;
  background: ${({color}) => color === "red" ? "linear-gradient(180deg, #850061, #EA33B4)" : "linear-gradient(180deg, #1E7644, #63C177)"};
  font-size: 16px;
  color: #E6F0E9;
`
